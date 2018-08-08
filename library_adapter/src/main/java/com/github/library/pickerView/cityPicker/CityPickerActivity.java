package com.github.library.pickerView.cityPicker;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.library.R;
import com.github.library.pickerView.cityPicker.adapter.AreaGridAdapter;
import com.github.library.pickerView.cityPicker.adapter.CityListAdapter;
import com.github.library.pickerView.cityPicker.adapter.ResultListAdapter;
import com.github.library.pickerView.cityPicker.db.DBManager;
import com.github.library.pickerView.cityPicker.model.City;
import com.github.library.pickerView.cityPicker.model.LocateState;
import com.github.library.pickerView.cityPicker.model.ProvinceInfo;
import com.github.library.pickerView.cityPicker.view.SideLetterBar;
import com.github.library.pickerView.scrollPicker.CustomCityPicker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class CityPickerActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String KEY_PICKED_CITY = "picked_city";

    private String location;//需要
    private boolean locateState;//需要

    private ListView mListView;
    private ListView mResultListView;
    private SideLetterBar mLetterBar;
    private EditText searchBox;
    private ImageView clearBtn;
    private ImageView backBtn;
    private ViewGroup emptyView;

    private CityListAdapter mCityAdapter;
    private ResultListAdapter mResultAdapter;
    private List<City> mAllCities;
    private DBManager dbManager;

    private TextView mTvCity;
    private TextView mTvAreaSelecter;
    private LinearLayout mLlShowCity;
    private PopupWindow popupWindow;

    private TextView mTvArea;
    private GridView gridView;
    private AreaGridAdapter areaAdapter;
    private int provinceSelect = 0;
    private int citySelect = 0;

    private List<String> provinces = new ArrayList<>();
    private List<List<String>> citys = new ArrayList<List<String>>();
    private List<List<List<String>>> countys = new ArrayList<List<List<String>>>();
    private List<CustomCityPicker.Province> provinceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cp_activity_city_list);
        mTvCity = (TextView) findViewById(R.id.tvCity);
        mTvArea = (TextView) findViewById(R.id.tvArea);
        mTvAreaSelecter = (TextView) findViewById(R.id.tvAreaSelecter);
        mLlShowCity = (LinearLayout) findViewById(R.id.llShowCity);

        String area = getIntent().getStringExtra("area");
        String city = getIntent().getStringExtra("city");


        initData();
        initView();

        if (city != null) {
            mTvCity.setText(city.substring(0, city.length() - 1));
            mTvArea.setText(area);
            mCityAdapter.updateLocateState(LocateState.SUCCESS, area);
        } else {
            mTvCity.setText(area);
            mCityAdapter.updateLocateState(LocateState.FAILED, area);

        }
//        initLocation();
        initJson();
    }

    private void initLocation() {
        if (locateState) {
            mCityAdapter.updateLocateState(LocateState.SUCCESS, location);
        } else {
            //定位失败
            mCityAdapter.updateLocateState(LocateState.FAILED, null);
        }
    }


    public void initJson() {
        provinceList.clear();
        provinces.clear();
        citys.clear();
        countys.clear();
        String json = getJson(this, "city.json");
        provinceList.addAll(JSON.parseArray(json, CustomCityPicker.Province.class));
        int provinceSize = provinceList.size();
        //添加省
        for (int x = 0; x < provinceSize; x++) {
            CustomCityPicker.Province pro;
            pro = provinceList.get(x);
            provinces.add(pro.getAreaName());
            List<CustomCityPicker.City> cities = pro.getCities();
            List<String> xCities = new ArrayList<String>();
            List<List<String>> xCounties = new ArrayList<List<String>>();
            int citySize = cities.size();
            //添加地市
            for (int y = 0; y < citySize; y++) {
                CustomCityPicker.City cit = cities.get(y);
                xCities.add(cit.getAreaName());
                List<CustomCityPicker.County> counties = cit.getCounties();
                List<String> yCounties = new ArrayList<String>();
                int countySize = counties.size();
                //添加区县
                if (countySize == 0) {
                    yCounties.add(cit.getAreaName());
                } else {
                    for (int z = 0; z < countySize; z++) {
                        yCounties.add(counties.get(z).getAreaName());
                    }
                }
                xCounties.add(yCounties);
            }
            citys.add(xCities);
            countys.add(xCounties);
        }
    }


    private void initData() {
        dbManager = new DBManager(this);
        dbManager.copyDBFile();
        mAllCities = dbManager.getAllCities();
        mCityAdapter = new CityListAdapter(this, mAllCities);
        // 城市点击事件
        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String name) {
                mTvCity.setText(name);
                if (name.equals("澳门") || name.equals("台湾") || name.equals("香港")) {
                    back(name);
                    return;
                }
                mTvArea.setText("");
                for (int i = 0; i < citys.size(); i++) {
                    for (int j = 0; j < citys.get(i).size(); j++) {
                        if (citys.get(i).get(j).contains(name)) {
                            provinceSelect = i;
                            citySelect = j;
                        }
                    }

                }
            }

            @Override
            public void onLocateClick() {
                //点击定位
//                mCityAdapter.updateLocateState(LocateState.LOCATING, null);
//                mLocationClient.startLocation();
            }
        });

        mResultAdapter = new ResultListAdapter(this, null);
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listview_all_city);
        mListView.setAdapter(mCityAdapter);
//        //TODO 添加头布局,城市区/县选择
//        View view = View.inflate(this, R.layout.cp_view_header, null);
//        mListView.addHeaderView(view);
//        mTvCity = (TextView) view.findViewById(R.id.tvCity);
//        mTvArea = (TextView) view.findViewById(R.id.tvArea);
//        mTvAreaSelecter = (TextView) view.findViewById(R.id.tvAreaSelecter);
//        mLlShowCity = (LinearLayout) view.findViewById(R.id.llShowCity);

        TextView overlay = (TextView) findViewById(R.id.tv_letter_overlay);
        mLetterBar = (SideLetterBar) findViewById(R.id.side_letter_bar);
        mLetterBar.setOverlay(overlay);
        mLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                mListView.setSelection(position);
            }
        });

        searchBox = (EditText) findViewById(R.id.et_search);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    clearBtn.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                    mResultListView.setVisibility(View.GONE);
                } else {
                    clearBtn.setVisibility(View.VISIBLE);
                    mResultListView.setVisibility(View.VISIBLE);
                    List<City> result = dbManager.searchCity(keyword);
                    if (result == null || result.size() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        mResultAdapter.changeData(result);
                    }
                }
            }
        });

        emptyView = (ViewGroup) findViewById(R.id.empty_view);
        mResultListView = (ListView) findViewById(R.id.listview_search_result);
        mResultListView.setAdapter(mResultAdapter);
        mResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                back(mResultAdapter.getItem(position).getName());
            }
        });

        clearBtn = (ImageView) findViewById(R.id.iv_search_clear);
        backBtn = (ImageView) findViewById(R.id.back);

        clearBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);

        mTvAreaSelecter.setOnClickListener(this);


    }

    public static String getJson(Context mContext, String fileName) {

        StringBuilder sb = new StringBuilder();
        AssetManager am = mContext.getAssets();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    am.open(fileName)));
            String next = "";
            while (null != (next = br.readLine())) {
                sb.append(next);
            }
        } catch (IOException e) {
            e.printStackTrace();
            sb.delete(0, sb.length());
        }
        return sb.toString().trim();
    }

    private void back(String city) {
        Intent data = new Intent();
        data.putExtra(KEY_PICKED_CITY, city);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_search_clear) {
            searchBox.setText("");
            clearBtn.setVisibility(View.GONE);
            emptyView.setVisibility(View.GONE);
            mResultListView.setVisibility(View.GONE);
        }
        if (i == R.id.back) {
            finish();
        }
        if (i == R.id.tvAreaSelecter) {
            initPopupWindow();
        }

    }

    private void initPopupWindow() {
        //LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentview = getLayoutInflater().inflate(R.layout.popup_area, null);
        contentview.setFocusable(true); // 这个很重要
        contentview.setFocusableInTouchMode(true);
        gridView = (GridView) contentview.findViewById(R.id.gridview_area);

        areaAdapter = new AreaGridAdapter(CityPickerActivity.this, countys.get(provinceSelect).get(citySelect));
        gridView.setAdapter(areaAdapter);
        popupWindow = new PopupWindow(contentview, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);


        popupWindow.setBackgroundDrawable(new ColorDrawable(0x11111111));
        // 设置好参数之后再show
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(mLlShowCity);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = countys.get(provinceSelect).get(citySelect).get(position);
                mTvArea.setText(name);
                popupWindow.dismiss();
                back(name);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mLocationClient.stopLocation();
    }
}
