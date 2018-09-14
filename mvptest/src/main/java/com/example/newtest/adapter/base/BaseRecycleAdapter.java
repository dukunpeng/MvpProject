package com.example.newtest.adapter.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.annotation.StringRes;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.newtest.R;

import java.util.List;

/**
 * Author：Mark
 * Date:2017/8/3  20:15
 * E-mail:dukunpeng22@163.com
 **/
public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter {

    private static final int TYPE_ITEM = 10;
    private static final int TYPE_FOOTER = 11;
    private static final int TYPE_HEADER = 12;
    private List<T> list;//数据集合
    private int itemLayout;//item的布局
    private Context context;
    //HeaderView, FooterView
    private View mHeaderView;
    private View mFooterView;


    /**
     * item的点击和长按监听
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //构造器list:要加载的数据集合，itemlayout:item的布局（R.layout.item）,isNeedMore:是否需要加载更多的功能（false:不需要，true:需要）
    public BaseRecycleAdapter(Context context, List<T> list, int itemLayout, boolean isNeedMore) {
        this.list = list;
        this.itemLayout = itemLayout;
        this.context = context;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getItemLayout() {
        return itemLayout;
    }

    public void setItemLayout(int itemLayout) {
        this.itemLayout = itemLayout;
    }

    //HeaderView和FooterView的get和set函数
    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getFooterView() {
        return mFooterView;
    }

    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }



    public void setScrolling(boolean scrolling){

    }
    @Override
    public int getItemViewType(int position) {
        if (position==0){
            if (mHeaderView!=null)
                return TYPE_HEADER;

        }
        if (position>=list.size()){
            if(mFooterView!=null)
                return TYPE_FOOTER;
        }
            return TYPE_ITEM;

    }

    //1、第一步，先在onCreateViewHolder里面添加item的布局，添加到RecyclerView里面
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            //返回加载更多的布局的holder
            return new FooterViewHolder(
                    mFooterView);
        }
        if (viewType ==TYPE_HEADER){
            return new FooterViewHolder(
                    mHeaderView);
        }
        else {
            //返回item的布局的holder
            View view = LayoutInflater.from(context).inflate(itemLayout, parent, false);
            return new MyViewHolder(view);
        }
    }


    //3、第三步，把ViewHolder传递到onBindViewHolder,进行item的数据绑定
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        //((MyViewHolder) holder).item.setText(position + "");
        if (TYPE_ITEM == getItemViewType(position) ) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mHeaderView!=null){
                        setPositionClick(position, list.get(position-1));
                    }else{
                        setPositionClick(position, list.get(position));
                    }

                }
            });
            //长按监听
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });
            if (mHeaderView!=null){
                initData((MyViewHolder) holder, position, list.get(position-1));
            }else{
                initData((MyViewHolder) holder, position, list.get(position));
            }

        }


    }

    @Override
    public int getItemCount() {
        if (list==null)
            return (mHeaderView==null?0:1)+(mFooterView==null?0:1);
            return list.size()+(mHeaderView==null?0:1)+(mFooterView==null?0:1);
    }

    /**
     * itemview 的点击事件（抽象方法）
     *
     * @param position
     */
    protected abstract void setPositionClick(int position, T bean);

    /**
     * 对item进行加载数据
     *
     * @param holder   recycleview的ViewHolder position位置
     * @param position
     */
    protected abstract void initData(MyViewHolder holder, int position, T bean);

    //2、第二步:在ViewHolder里面初始化视图
    //如果你的item布局里面有别的视图可以自己添加对应的方法就是了
    //目前里面没有写那么多
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View findView;
        private View convertView;
        private SparseArray<Object> tags;
        //用来替代Map<Integer,Object>的容器, 效率比map高
        private SparseArray<View> views;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.convertView = itemView;
            this.findView = itemView;
            tags = new SparseArray<>();
            views = new SparseArray<View>();
        }

        public View getConverView() {
            return convertView;
        }

        public View getFindView() {
            return findView;
        }

        public <V extends View> V getView(@IdRes int viewId) {
            View view;
            Object viewObj = findView.getTag(viewId);
            if (null != viewObj) {
                view = (View) viewObj;
            } else {
                view = findView.findViewById(viewId);
                findView.setTag(viewId, view);
            }
            return (V) view;
        }

        /**
         * 设置指定的id视图的可见性
         *
         * @param viewId
         * @param visibility
         * @return
         */
        public MyViewHolder setViewVisibility(@IdRes int viewId, int visibility) {
            if (getView(viewId) != null) {
                getView(viewId).setVisibility(visibility);
            }
            return this;
        }

        /**
         * 根据 viewId 获取一个 AppCompatImageView 对象
         */
        public AppCompatImageView getAppCompatImageView(@IdRes int viewId) {
            return (AppCompatImageView) getView(viewId);
        }
        /**
         * 根据 viewId 获取一个 ImageView 对象
         */
        public ImageView getImageView(@IdRes int viewId) {
            return (ImageView) getView(viewId);
        }

        /**
         * 根据 viewId 获取一个 TextView 对象
         */
        public TextView getTextView(@IdRes int viewId) {
            return (TextView) getView(viewId);
        }
        /**
         * 根据 viewId 获取一个 ListView 对象
         */
        public ListView getListView(@IdRes int viewId) {
            return (ListView) getView(viewId);
        }

        /**
         * 根据 viewId 获取一个 RecyclerView 对象
         */
        public RecyclerView getRecyclerView(@IdRes int viewId) {
            return (RecyclerView) getView(viewId);
        }

        /**
         * 根据 viewId 获取一个 CheckBox 对象
         */
        public CheckBox getCheckBox(@IdRes int viewId) {
            return (CheckBox) getView(viewId);
        }

        /**
         * 为指定 viewId 的 ImageView 对象设置图片
         */
        public MyViewHolder setImageDrawable(@IdRes int viewId, @Nullable Drawable drawable) {
            if (getImageView(viewId) != null) {
                getImageView(viewId).setImageDrawable(drawable);
            }
            return this;
        }

        /**
         * 为指定 viewId 的 ImageView 对象设置图片
         */
        public MyViewHolder setImageResource(@IdRes int viewId, @DrawableRes int resId) {
            if (getImageView(viewId) != null) {
                getImageView(viewId).setImageResource(resId);
            }
            return this;
        }

        /**
         * 为指定 viewId 的 ImageView 对象设置图片
         */
        public MyViewHolder setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
            if (getImageView(viewId) != null) {
                getImageView(viewId).setImageBitmap(bitmap);
            }
            return this;
        }

        /**
         * 未指定的viewId 的ImageView 对象设置背景图片
         *
         * @param viewId
         * @param drawable
         * @return
         */
        public MyViewHolder setImageBackgroud(@IdRes int viewId, @Nullable Drawable drawable) {
            if (getImageView(viewId) != null) {
                getImageView(viewId).setBackgroundDrawable(drawable);
            }
            return this;
        }

        /**
         * 使用 Glide 为指定 viewId 的 ImageView 对象设置图片
         */
        public MyViewHolder bindImage(@IdRes int viewId, @NonNull String url) {
            if (getImageView(viewId) != null) {
                Glide.with(context).load(url).into(getImageView(viewId));
            }
            return this;
        }
        /**
         * 使用 Glide 为指定 viewId 的 ImageView 对象设置图片
         */
        public MyViewHolder bindImageWithCache(@IdRes int viewId, @NonNull String url) {
            if (getImageView(viewId) != null) {
                if (!TextUtils.isEmpty(url)){
                    if (!url.equals(getImageView(viewId).getTag())){
                        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(getImageView(viewId));
                    }
                }

            }
            return this;
        }
        /**
         * 使用 Glide 为指定 viewId 的 ImageView 对象设置图片
         */
        public MyViewHolder bindImageWithCache(@IdRes int viewId, @NonNull String url, String tag) {
            final ImageView imageView = getImageView(viewId);
            if ( imageView!= null) {
                if (!TextUtils.isEmpty(url)){
                    if (!tag.equals(imageView.getTag(R.id.image_tag))){
                        imageView.setTag(R.id.image_tag,tag);
                        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
                    }
                }

            }
            return this;
        }

        /**
         * 使用 Glide 为指定 viewId 的 ImageView 对象设置图片
         */
        public MyViewHolder bindImage(@IdRes final int viewId, @NonNull String url, final int corner) {
            if (getImageView(viewId) != null) {
                Glide.with(context).load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).into(new BitmapImageViewTarget(getImageView(viewId)){
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(corner); //设置圆角弧度
                        getImageView(viewId).setImageDrawable(circularBitmapDrawable);
                    }
                });
            }
            return this;
        }

        /**
         * 使用 Glide 为指定 viewId 的 ImageView 对象设置图片
         */
        public MyViewHolder bindImage(@IdRes final int viewId, @NonNull String url, final int corner , final int errorRes) {
            if (getImageView(viewId) != null) {
                Glide.with(context).load(url).asBitmap().error(errorRes).diskCacheStrategy(DiskCacheStrategy.ALL).into(new BitmapImageViewTarget(getImageView(viewId)){
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(corner); //设置圆角弧度
                        getImageView(viewId).setImageDrawable(circularBitmapDrawable);
                    }
                });
            }
            return this;
        }


        /**
         * 为指定 viewId 的 TextView 对象设置文字
         */
        public MyViewHolder setText(@IdRes int viewId, @StringRes int resid) {
            if (getTextView(viewId) != null) {
                getTextView(viewId).setText(resid);
            }
            return this;
        }

        /**
         * 为指定 viewId 的 TextView 对象设置文字
         */
        public MyViewHolder setText(@IdRes int viewId, CharSequence text) {
            if (getTextView(viewId) != null) {
                getTextView(viewId).setText(text);
            }
            return this;
        }

        /**
         * 为指定 viewId 的 TextView 对象设置文字
         */
        public MyViewHolder setText(@IdRes int viewId, CharSequence text, Typeface typeface) {
            if (getTextView(viewId) != null) {
                getTextView(viewId).setTypeface(typeface);
                getTextView(viewId).setText(text);
            }
            return this;
        }

        public MyViewHolder setTextSize(@IdRes int viewId, @Size int size) {
            if (getTextView(viewId) != null) {
                getTextView(viewId).setTextSize(size);
            }
            return this;
        }


        /**
         * 为指定 viewId 的 TextView 对象设置文字颜色
         */
        public MyViewHolder setTextColor(@IdRes int viewId, @ColorInt int color) {
            if (getTextView(viewId) != null) {
                getTextView(viewId).setTextColor(color);
            }
            return this;
        }

        /**
         * 为指定 viewId 的 View 对象设置 TAG
         */
        public MyViewHolder setTag(@IdRes int viewId, final Object tag) {
            if (getView(viewId) != null) {
                getView(viewId).setTag(tag);
            }
            return this;
        }

        /**
         * 为指定 viewId 的 View 对象设置背景图片
         */
        public MyViewHolder setBackgroundResource(@IdRes int viewId, @DrawableRes int resid) {
            if (getView(viewId) != null) {
                getView(viewId).setBackgroundResource(resid);
            }
            return this;
        }

        /**
         * 使用当前 ViewHolder 记录一个 TAG
         */
        public MyViewHolder putTag(int key, final Object tag) {
            tags.put(key, tag);
            return this;
        }

        /**
         * 从当前 ViewHolder 中取出一个TAG
         */
        public Object getTag(int key) {
            return tags.get(key);
        }

        /**
         * 为指定 viewId 的 CheckBox 对象设置选中状态
         */
        public MyViewHolder setChecked(@IdRes int viewId, boolean checked) {
            if (getCheckBox(viewId) != null) {
                getCheckBox(viewId).setChecked(checked);
            }
            return this;
        }

        /**
         * 切换指定 viewId 的 CheckBox 的选中状态
         */
        public MyViewHolder toggle(@IdRes int viewId) {
            if (getCheckBox(viewId) != null) {
                getCheckBox(viewId).toggle();
            }
            return this;
        }

        /**
         * 为指定 viewId 的 View 对象设置点击监听
         */
        public MyViewHolder setOnClickListener(@IdRes int viewId, @Nullable View.OnClickListener listener) {
            if (getView(viewId) != null) {
                getView(viewId).setOnClickListener(listener);
            }

            return this;
        }

        /**
         * 为指定 viewId 的 View 对象设置是否可见
         */
        public MyViewHolder setVisibile(@IdRes int viewId, boolean visible) {
            if (getView(viewId) != null) {
                getView(viewId).setVisibility(visible ? View.VISIBLE : View.GONE);
            }
            return this;
        }

        /**
         * 为指定 viewId 的 View 对象设置是否可见
         */
        public MyViewHolder setVisibility(@IdRes int viewId, int visibility) {
            if (getView(viewId) != null) {
                getView(viewId).setVisibility(visibility);
            }
            return this;
        }

        /**
         * 为指定 viewId 的 View 对象设置布局参数
         */
        public MyViewHolder setLayoutParams(@IdRes int viewId, RelativeLayout.LayoutParams params) {
            if (getView(viewId) != null) {
                getView(viewId).setLayoutParams(params);
            }
            return this;
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
    public class HeadererViewHolder extends RecyclerView.ViewHolder {

        public HeadererViewHolder(View itemView) {
            super(itemView);
        }
    }
}