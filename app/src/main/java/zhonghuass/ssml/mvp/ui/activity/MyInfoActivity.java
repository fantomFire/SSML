package zhonghuass.ssml.mvp.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.github.library.pickerView.scrollPicker.CustomCityPicker;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lljjcoder.citypickerview.widget.CityPicker;

import butterknife.BindView;
import butterknife.OnClick;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerMyInfoComponent;
import zhonghuass.ssml.di.module.MyInfoModule;
import zhonghuass.ssml.mvp.contract.MyInfoContract;
import zhonghuass.ssml.mvp.presenter.MyInfoPresenter;
import zhonghuass.ssml.mvp.ui.MBaseActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MyInfoActivity extends MBaseActivity<MyInfoPresenter> implements MyInfoContract.View {

    @BindView(R.id.tv_area)
    TextView tvArea;

    private CustomCityPicker cityPicker;
    private Dialog cityPickerDialog;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .myInfoModule(new MyInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        cityPicker = new CustomCityPicker(this, new CustomCityPicker.ResultHandler() {
            @Override
            public void handle(String result) {
                tvArea.setText(result);
            }
        });
        //提前初始化数据，这样可以加载快一些。
        cityPicker.initJson();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(this)
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("江苏省")
                .city("常州市")
                .district("天宁区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(true)
                .districtCyclic(true)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                //为TextView赋值
                tvArea.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
            }
        });
    }


    @OnClick({R.id.ll_area})
    public void onViewClicked(View view) {
        super.onViewClicked(view);
        switch (view.getId()) {
            case R.id.ll_area:
                cityPicker.show("陕西省-西安市-雁塔区");
//                selectAddress();
//                startActivity(new Intent().setClass(this, CitySelectActivity.class));
                break;
        }
    }
}
