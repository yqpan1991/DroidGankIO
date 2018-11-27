package apollo.edus.com.share.ui.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import apollo.edus.com.share.R;
import apollo.edus.com.share.message.BaseShareMessage;
import apollo.edus.com.share.unit.BaseShareUnit;

/**
 * Description.
 *
 * @author panda
 */
public class ShareFragment extends DialogFragment {
    public static final String KEY_SHARE_UNIT_LIST = "share_unit";
    public static final String KEY_SHARE_MESSAGE = "share_msg";
    private RecyclerView mRecyclerView;
    private List<BaseShareUnit> mBaseShareUnitList;
    private BaseShareMessage mShareMessage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBaseShareUnitList = new ArrayList<>();
        Bundle arguments = getArguments();
        if(arguments != null){
            mBaseShareUnitList = (List<BaseShareUnit>) arguments.getSerializable(KEY_SHARE_UNIT_LIST);
            mShareMessage = (BaseShareMessage) arguments.getSerializable(KEY_SHARE_MESSAGE);
        }
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

        Window window = getDialog().getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0.55f;
        window.setAttributes(params);


        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4, GridLayoutManager.VERTICAL, false));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity());
        mRecyclerView.setAdapter(adapter);
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
                mIvThumb.setImageBitmap(null);
                mTvContent.setText(baseShareUnit.getUnitInfo().getDisplayName());
            }
        }


        @Override
        public void onClick(View v) {
            if(mShareUnit != null){
                mShareUnit.share(getActivity(), mShareMessage, null);
                dismiss();
            }
        }
    }

}
