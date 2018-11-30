package apollo.edus.com.share.ui.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import apollo.edus.com.share.IShareCallback;
import apollo.edus.com.share.R;
import apollo.edus.com.share.message.BaseShareMessage;
import apollo.edus.com.share.unit.BaseShareUnit;
import apollo.edus.com.share.unit.IShareUnit;
import apollo.edus.com.share.unit.UnitInfo;
import apollo.edus.com.share.utils.DensityUtils;

/**
 * Description.
 *
 * @author panda
 */
public class ShareFragment extends DialogFragment {
    private RecyclerView mRecyclerView;
    private List<BaseShareUnit> mBaseShareUnitList;
    private BaseShareMessage mShareMessage;
    private IShareCallback mShareCallback;
    private RecyclerViewAdapter mAdapter;
    private boolean mIsFromSaveState;

    public ShareFragment(){
        mBaseShareUnitList = new ArrayList<>();
    }

    public void setShareUnitList(List<BaseShareUnit> shareUnitList){
        mBaseShareUnitList.clear();
        if(shareUnitList != null && !shareUnitList.isEmpty()){
            mBaseShareUnitList.addAll(shareUnitList);
        }
        if(mAdapter != null){
            mAdapter.notifyDataSetChanged();
        }

    }

    public void setShareMessage(BaseShareMessage shareMessage){
        mShareMessage = shareMessage;
    }

    public void setShareCallback(IShareCallback shareCallback){
        mShareCallback = shareCallback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.share_fragment_layout, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(this.getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(new FrameLayout(getActivity()));
        return dialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.rv_content);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = DensityUtils.dp2Px(getActivity(), 300);
        window.setAttributes(wlp);


        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4, GridLayoutManager.VERTICAL, false));
        mAdapter = new RecyclerViewAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        if(savedInstanceState != null){
            mIsFromSaveState = true;
            dismiss();
        }
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder>{
        private LayoutInflater mInflater;


        public RecyclerViewAdapter(Activity context){
            mInflater = LayoutInflater.from(context);
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.updateData(mBaseShareUnitList.get(position), position);
        }

        @Override
        public int getItemCount() {
            return mBaseShareUnitList.size();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mIvThumb;
        private TextView mTvContent;
        private BaseShareUnit mShareUnit;
        private int mPosition;

        public ViewHolder(ViewGroup parentView) {
            super(LayoutInflater.from(parentView.getContext()).inflate(R.layout.share_item, parentView, false));
            mIvThumb = itemView.findViewById(R.id.iv_avatar);
            mTvContent = itemView.findViewById(R.id.tv_content);
            itemView.setOnClickListener(this);
        }

        public void updateData(BaseShareUnit baseShareUnit, int position){
            mShareUnit = baseShareUnit;
            mPosition = position;
            if(baseShareUnit == null || baseShareUnit.getUnitInfo() == null){
                mIvThumb.setImageBitmap(null);
                mTvContent.setText(null);
            }else{
                UnitInfo unitInfo = baseShareUnit.getUnitInfo();
                if(unitInfo.getIconResId() > 0){
                    mIvThumb.setImageResource(unitInfo.getIconResId());
                }else if(unitInfo.getIconDrawable() != null){
                    mIvThumb.setImageDrawable(unitInfo.getIconDrawable());
                }else if(!TextUtils.isEmpty(unitInfo.getIconLocalPath())){
                    //todo: 加载本地图片
                    mIvThumb.setImageBitmap(null);
                }else if(!TextUtils.isEmpty(unitInfo.getIconThumbUrl())){
                    //todo: 加载网络图片
                    mIvThumb.setImageBitmap(null);
                }else{
                    mIvThumb.setImageBitmap(null);
                }
                mTvContent.setText(baseShareUnit.getUnitInfo().getDisplayName());
            }
        }


        @Override
        public void onClick(View v) {
            if(mShareUnit != null){
                mShareUnit.share(getActivity(), mShareMessage, new IShareCallback() {
                    @Override
                    public void onStartShare(IShareUnit sharePlugin, BaseShareMessage shareMessage) {
                        if(mShareCallback != null){
                            mShareCallback.onStartShare(sharePlugin, shareMessage);
                        }
                    }

                    @Override
                    public void onShareSucceed(IShareUnit sharePlugin, BaseShareMessage shareMessage) {
                        if(mShareCallback != null){
                            mShareCallback.onShareSucceed(sharePlugin, shareMessage);
                            mShareCallback = null;
                        }
                        dismiss();
                    }

                    @Override
                    public void onShareFailed(IShareUnit sharePlugin, BaseShareMessage shareMessage, String code, String reason) {
                        if(mShareCallback != null){
                            mShareCallback.onShareFailed(sharePlugin, shareMessage, code, reason);
                            mShareCallback = null;
                        }
                        dismiss();
                    }
                });
                dismiss();
            }
        }
    }

}
