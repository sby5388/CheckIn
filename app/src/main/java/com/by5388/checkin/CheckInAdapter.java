package com.by5388.checkin;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.by5388.checkin.databinding.ItemCheckInBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Administrator  on 2020/1/9.
 */
public class CheckInAdapter extends RecyclerView.Adapter<CheckInAdapter.ViewHolder> {

    private List<CheckInItem> mCheckInItems;

    CheckInAdapter() {
        mCheckInItems = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            mCheckInItems.add(new CheckInItem(i, "name" + i, i, "device" + i));
        }
    }

    public void setCheckInItems(List<CheckInItem> checkInItems) {
        mCheckInItems = checkInItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ItemCheckInBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_check_in, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CheckInItem checkInItem = mCheckInItems.get(position);
        holder.bind(checkInItem);
    }

    @Override
    public int getItemCount() {
        return mCheckInItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemCheckInBinding mBinding;

        ViewHolder(@NonNull ItemCheckInBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void bind(CheckInItem item) {
            mBinding.setViewModel(new CheckInViewModel(item));
        }
    }
}
