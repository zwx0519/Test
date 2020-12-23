package com.example.test.ui.shop.home.category;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.test.R;
import com.example.test.adapter.shop.home.category.CategoryBigImageAdapter;
import com.example.test.adapter.shop.home.category.CategoryButtomInfoAdapter;
import com.example.test.adapter.shop.home.category.CategoryIssueAdapter;
import com.example.test.adapter.shop.home.category.CategoryParameterAdapter;
import com.example.test.base.BaseAdapter;
import com.example.test.model.bean.shop.shoppingcar.AddShoppingCarBean;
import com.example.test.ui.shop.ShopActivity;
import com.example.test.ui.shop.home.category.bigpic.BigImageActivity;
import com.example.test.app.MyApp;
import com.example.test.base.BaseActivity;
import com.example.test.model.bean.shop.home.category.CategoryBean;
import com.example.test.model.bean.shop.home.category.CategoryBottomInfoBean;
import com.example.test.presenter.shop.home.category.CategoryPresenter;
import com.example.test.ui.shop.login.LoginActivity;
import com.example.test.utils.ImageLoaderUtils;
import com.example.test.utils.ItemDecoration;
import com.example.test.utils.SpUtils;
import com.example.test.utils.ToastUtils;
import com.example.test.view.shop.home.category.ICategory;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

public class CategoryActivity extends BaseActivity<ICategory.Persenter> implements ICategory.View {

    //    @BindView(R.id.webView_category)
//    WebView webView;
    @BindView(R.id.mRlv_category_all)
    RecyclerView mRlv_all;//底部列表数据
    @BindView(R.id.mRlv_category_issue)
    RecyclerView mRlv_issue;//常见问题
    @BindView(R.id.mRlv_category_parameter)
    RecyclerView mRlv_parameter;//商品参数
    @BindView(R.id.mRlv_category_bigimage)
    RecyclerView mRlv_bigimage;//图片

    @BindView(R.id.banner_category)
    Banner banner;//Banner
    @BindView(R.id.tv_category_info_title)
    TextView tv_title;
    @BindView(R.id.tv_category_info_desc)
    TextView tv_desc;
    @BindView(R.id.tv_category_info_price)
    TextView tv_price;

    @BindView(R.id.tv_category_info_comment_head_name)
    TextView tv_head_name;
    @BindView(R.id.tv_category_addCar)
    TextView tv_addCar;
    @BindView(R.id.tv_category_info_comment_head_desc)
    TextView tv_head_desc;
    @BindView(R.id.tv_category_info_comment_head_date)
    TextView tv_head_date;
    @BindView(R.id.iv_category_info_comment_head_img)
    ImageView iv_head_img;
    @BindView(R.id.iv_category_info_comment_img)
    ImageView iv_img;
    @BindView(R.id.mCl_category_assess)
    ConstraintLayout mCl_assess;
    @BindView(R.id.mCl_category_comment)
    ConstraintLayout mCl_comment;
    private boolean isSelect = false;

    private String h5 = "<html>\n" +
            "            <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no\"/>\n" +
            "            <head>\n" +
            "                <style>\n" +
            "                    p{\n" +
            "                        margin:0px;\n" +
            "                    }\n" +
            "                    img{\n" +
            "                        width:100%;\n" +
            "                        height:auto;\n" +
            "                    }\n" +
            "                </style>\n" +
            "            </head>\n" +
            "            <body>\n" +
            "                word\n" +
            "            </body>\n" +
            "        </html>";

    private ArrayList<CategoryBottomInfoBean.DataBean.GoodsListBean> goodsList;//底部列表集合
    private ArrayList<CategoryBean.DataBeanX.IssueBean> issuelist;//常见问题集合
    private ArrayList<CategoryBean.DataBeanX.AttributeBean> attributeList;//商品参数集合
    private CategoryButtomInfoAdapter categoryButtomInfoAdapter;
    private CategoryIssueAdapter categoryIssueAdapter;
    private CategoryParameterAdapter categoryParameterAdapter;
    private Button btn_add;
    private Button btn_minus;
    private TextView tv_count;
    int count;
    private CategoryBean.DataBeanX.InfoBean info;
    private PopupWindow popupWindow;

    private Intent intent;
    private int id;
    private List<CategoryBean.DataBeanX.ProductListBean> productList;

    @Override
    protected int getLayout() {
        return R.layout.activity_category;
    }

    @Override
    protected void initView() {
        initViewIssue();//常见问题布局
        initBottomInfo();//底部列表数据
        initViewParameter();//商品参数

        //判断购物车选中状态
        if (isSelect == false) {
            isSelect = true;
        } else {
            isSelect = false;
        }

        //广播
        intent = new Intent();
        intent.setAction("shu");
    }

    @OnClick({R.id.fl_collect, R.id.fl_car, R.id.tv_category_buy, R.id.tv_category_addCar})
    public void onClick(View view) {
        if (!TextUtils.isEmpty(SpUtils.getInstance().getString("token"))) {
            switch (view.getId()) {
                case R.id.fl_collect:
                    break;
                case R.id.fl_car:
                    Intent intent = new Intent(CategoryActivity.this, ShopActivity.class);
                    intent.putExtra("pos",3);
                    startActivity(intent);
                    break;
                case R.id.tv_category_buy:

                    break;
                case R.id.tv_category_addCar:
                    //TODO 点击加入购物车弹出购物车弹框
                    if(isSelect){ //购物车进行显示隐藏
                        initPopu();//添加时
                    }else {
                        initPopu_ok();//添加成功关闭弹窗
                    }
                    break;
            }
        } else {
            Intent i = new Intent(CategoryActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }

    //TODO 添加成功关闭弹窗
    private void initPopu_ok() {
        popupWindow.dismiss();

        //添加购物车goodsId
        int goodsId = info.getId();
        intent.putExtra("goodsId",goodsId);
        sendBroadcast(intent);//发送广播
        //添加购物车number
        String number = tv_count.getText().toString();
        intent.putExtra("number",number);
        sendBroadcast(intent);
        //添加购物车productId
        int productId = productList.get(0).getId();
        intent.putExtra("productId",productId);
        sendBroadcast(intent);

        View join_view = LayoutInflater.from(CategoryActivity.this).inflate(R.layout.layout_shoppingcar_popu_ok, null);
        PopupWindow popupWindow1 = new PopupWindow(join_view, 200, 200);

        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.alpha = 0.5f;
        getWindow().setAttributes(attributes);

        popupWindow1.showAtLocation(tv_addCar, Gravity.CENTER, 0, 0);

        //两秒自动关闭
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        popupWindow1.dismiss();
                        WindowManager.LayoutParams attributes = getWindow().getAttributes();
                        attributes.alpha = 1f;
                        getWindow().setAttributes(attributes);
                    }
                });
            }
        },1000,2000);

        isSelect = true;
    }

    //TODO 购物车弹窗
    private void initPopu() {
        //pop
        View join_view = LayoutInflater.from(CategoryActivity.this).inflate(R.layout.layout_shoppingcar_popu, null);
        popupWindow = new PopupWindow(join_view, GridLayout.LayoutParams.MATCH_PARENT, 300);

        popupWindow.showAtLocation(tv_addCar, Gravity.BOTTOM, 0, 70);

        ImageView image_pop = join_view.findViewById(R.id.iv_image_shoppingcar_pop);
        TextView price_pop = join_view.findViewById(R.id.tv_price_shoppingcar_pop);
        btn_add = join_view.findViewById(R.id.btn_add_shoppingcar_pop);
        btn_minus = join_view.findViewById(R.id.btn_minus_shoppingcar_pop);
        tv_count = join_view.findViewById(R.id.tv_count_shoppingcar_pop);
        TextView tv_back = join_view.findViewById(R.id.tv_return_shopping_pop);

        Glide.with(CategoryActivity.this).load(this.info.getList_pic_url()).into(image_pop);
        price_pop.setText("价格:  ￥"+ this.info.getRetail_price() + "");
        count = 1;

        ClickListener clickListener = new ClickListener();
        btn_add.setOnClickListener(clickListener);
        btn_minus.setOnClickListener(clickListener);


        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();//关闭弹窗
            }
        });
        isSelect=false;
    }

    //TODO 点击+ -的时候对里面的数字进行修改
    class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_add_shoppingcar_pop:
                    count++;
                    if (count > 0) {
                        tv_count.setText(count + "");
                    }
                    break;
                case R.id.btn_minus_shoppingcar_pop:
                    count--;
                    if (count > 0) {
                        tv_count.setText(count + "");
                    }
                    break;
            }
        }
    }

    //TODO 商品参数布局
    private void initViewParameter() {
        attributeList = new ArrayList<>();
        mRlv_parameter.setLayoutManager(new LinearLayoutManager(this));
        categoryParameterAdapter = new CategoryParameterAdapter(this, attributeList);
        mRlv_parameter.setAdapter(categoryParameterAdapter);
    }

    //TODO 常见问题布局
    private void initViewIssue() {
        issuelist = new ArrayList<>();
        mRlv_issue.setLayoutManager(new LinearLayoutManager(this));
        categoryIssueAdapter = new CategoryIssueAdapter(this, issuelist);
        mRlv_issue.setAdapter(categoryIssueAdapter);
    }

    //TODO 底部列表数据
    private void initBottomInfo() {
        goodsList = new ArrayList<>();
        mRlv_all.setLayoutManager(new GridLayoutManager(this, 2));
        mRlv_all.addItemDecoration(new ItemDecoration(this));
        categoryButtomInfoAdapter = new CategoryButtomInfoAdapter(this, goodsList);
        mRlv_all.setAdapter(categoryButtomInfoAdapter);
    }

    @Override
    protected ICategory.Persenter createPersenter() {
        return new CategoryPresenter(this);
    }

    @Override
    protected void initData() {
        persenter = new CategoryPresenter(this);

        //接收ID
        String categoryId = (String) MyApp.getMap().get("categoryId");
        if (categoryId != null) {
            persenter.getCategory(categoryId);
            persenter.getCategoryBottomInfo(categoryId);
        } else {
            ToastUtils.s(this, getString(R.string.tips_error_goodid));
        }

    }

    //TODO 居家 商品详情购买页
    @Override
    public void getCategoryReturn(CategoryBean result) {
        if (result != null) {
            //Banner
            initBanner(result.getData());
            //Banner下面的展示数据
            initInfo(result.getData().getInfo());
            //评论
            initComment(result.getData().getComment().getData());
            //常见问题数据
            initIssue(result.getData().getIssue());
            //h5 商品详情
            //initGoodDetail(result.getData().getInfo().getGoods_desc());
            //展示goods_desc
            showImage(result.getData().getInfo().getGoods_desc());
            //商品参数
            initParameter(result.getData().getAttribute());

            info = result.getData().getInfo();
            productList = result.getData().getProductList();
        }
    }

    //TODO H5展示图片 大图
    private void showImage(String goods_desc) {
        ArrayList<String> listUrl = new ArrayList<>();
        String img = "<img[\\s\\S]*?>";
        Pattern pattern = Pattern.compile(img);
        Matcher matcher = pattern.matcher(goods_desc);

        while (matcher.find()) {
            String word = matcher.group();
            int start = word.indexOf("\"") + 1;
            int end = word.indexOf(".jpg");
            if (end > 0) {//如果是jpg格式的就截取jpg
                String url = word.substring(start, end);
                if (url != null) {
                    url = url + ".jpg";
                    listUrl.add(url);
                } else {
                    return;
                }
            } else {
                int end1 = word.indexOf(".png");//如果是png格式的就截取png
                String url = word.substring(start, end1);
                if (url != null) {
                    url = url + ".png";
                    listUrl.add(url);
                } else {
                    return;
                }
            }
        }

        mRlv_bigimage.setLayoutManager(new LinearLayoutManager(this));
        CategoryBigImageAdapter categoryBigImageAdapter = new CategoryBigImageAdapter(this, listUrl);
        mRlv_bigimage.setAdapter(categoryBigImageAdapter);

        //点击条目跳转
        categoryBigImageAdapter.addListClick(new BaseAdapter.IListClick() {
            @Override
            public void itemClick(int pos) {
                Intent intent = new Intent(CategoryActivity.this, BigImageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("image", listUrl);
                bundle.putInt("postion", pos);//点击哪个就传哪个下标
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });

//        LinearLayout id = findViewById(R.id.home__detail_info_30_ll);
//        id.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putStringArrayList("image",listUrl);
//                Intent intent = new Intent(CategoryActivity.this, BigImageActivity.class);
//                intent.putExtra("bundle", bundle);
//                startActivity(intent);
//            }
//        });

    }

    //TODO 评论
    private void initComment(CategoryBean.DataBeanX.CommentBean.DataBean data) {
        if (data != null) {
            mCl_assess.setVisibility(View.VISIBLE);//进行显示评论
            mCl_comment.setVisibility(View.VISIBLE);//显示商品文字

            //时间
            tv_head_date.setText(data.getAdd_time());
            //名字
            tv_head_name.setText(data.getNickname());
            //评论内容
            tv_head_desc.setText(data.getContent());
            //底部图片
            if (data.getPic_list() != null && data.getPic_list().size() > 0) {
                ImageLoaderUtils.loadImage(data.getPic_list().get(0).getPic_url(), iv_img);
            } else {
                mCl_comment.setVisibility(View.GONE);//隐藏下面的图片
            }
        } else {
            Log.i("TAG", "该详情没有评论");
        }

    }

    //TODO 商品 详情购买页 底部数据列表
    @Override
    public void getCategoryBottomInfoReturn(CategoryBottomInfoBean result) {
        List<CategoryBottomInfoBean.DataBean.GoodsListBean> data = result.getData().getGoodsList();
        goodsList.addAll(data);
        categoryButtomInfoAdapter.notifyDataSetChanged();
    }

    //TODO Banner下面的展示数据
    private void initInfo(CategoryBean.DataBeanX.InfoBean info) {
        tv_title.setText(info.getName());
        tv_desc.setText(info.getGoods_brief());
        tv_price.setText("￥" + info.getRetail_price());
    }

    //TODO Banner数据
    private void initBanner(CategoryBean.DataBeanX data) {
        banner.setImages(data.getGallery()).setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                CategoryBean.DataBeanX.GalleryBean bean = (CategoryBean.DataBeanX.GalleryBean) path;
                Glide.with(context).load(bean.getImg_url()).into(imageView);
            }
        }).start();
    }

    //TODO 商品参数数据
    private void initParameter(List<CategoryBean.DataBeanX.AttributeBean> attribute) {
        attributeList.addAll(attribute);
        categoryParameterAdapter.notifyDataSetChanged();
    }

    //TODO 常见问题数据
    private void initIssue(List<CategoryBean.DataBeanX.IssueBean> issue) {
        issuelist.addAll(issue);
        categoryIssueAdapter.notifyDataSetChanged();
    }

    //TODO h5 商品详情数据
    private void initGoodDetail(String webData) {
        String content = h5.replace("word", webData);
        Log.i("TAG", content);
        //webView.loadDataWithBaseURL("about:blank", content, "text/html", "utf-8", null);
    }
}