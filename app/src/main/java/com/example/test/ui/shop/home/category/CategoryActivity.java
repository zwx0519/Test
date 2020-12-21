package com.example.test.ui.shop.home.category;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.test.R;
import com.example.test.adapter.shop.home.category.CategoryButtomInfoAdapter;
import com.example.test.adapter.shop.home.category.CategoryIssueAdapter;
import com.example.test.adapter.shop.home.category.CategoryParameterAdapter;
import com.example.test.app.MyApp;
import com.example.test.base.BaseActivity;
import com.example.test.model.bean.shop.home.category.CategoryBean;
import com.example.test.model.bean.shop.home.category.CategoryBottomInfoBean;
import com.example.test.presenter.shop.home.category.CategoryPresenter;
import com.example.test.utils.ImageLoaderUtils;
import com.example.test.utils.ItemDecoration;
import com.example.test.view.shop.home.category.ICategory;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CategoryActivity extends BaseActivity<ICategory.Persenter> implements ICategory.View {

    @BindView(R.id.webView_category)
    WebView webView;
    @BindView(R.id.mRlv_category_all)
    RecyclerView mRlv_all;//底部列表数据
    @BindView(R.id.mRlv_category_issue)
    RecyclerView mRlv_issue;//常见问题
    @BindView(R.id.mRlv_category_parameter)
    RecyclerView mRlv_parameter;//商品参数
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


    @Override
    protected int getLayout() {
        return R.layout.activity_category;
    }

    @Override
    protected void initView() {
        initViewIssue();//常见问题布局
        initBottomInfo();//底部列表数据
        initViewParameter();//商品参数
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
        String categoryId = (String) MyApp.getMap().get("categoryId");
        if (categoryId != null) {
            persenter.getCategory(categoryId);
            persenter.getCategoryBottomInfo(categoryId);
        } else {
            showToast(getString(R.string.tips_error_goodid), Toast.LENGTH_SHORT);
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
            initGoodDetail(result.getData().getInfo().getGoods_desc());
            //商品参数
            initParameter(result.getData().getAttribute());
        }

    }

    //TODO 评论
    private void initComment(CategoryBean.DataBeanX.CommentBean.DataBean data) {
        if (data != null ) {
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
        }else {
            Log.i("TAG","该详情没有评论");
        }

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
        webView.loadDataWithBaseURL("about:blank", content, "text/html", "utf-8", null);
    }

    //TODO 商品 详情购买页 底部数据列表
    @Override
    public void getCategoryBottomInfoReturn(CategoryBottomInfoBean result) {
        List<CategoryBottomInfoBean.DataBean.GoodsListBean> data = result.getData().getGoodsList();
        goodsList.addAll(data);
        categoryButtomInfoAdapter.notifyDataSetChanged();
    }

}