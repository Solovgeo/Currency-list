package com.solovgeo.presentation.base;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;

public final class NotifyFirstItemChangeAdapterListUpdateCallback implements ListUpdateCallback {
    @NonNull
    private final RecyclerView.Adapter mAdapter;

    /**
     * Creates an AdapterListUpdateCallback that will dispatch update events to the given adapter.
     *
     * @param adapter The Adapter to send updates to.
     */
    public NotifyFirstItemChangeAdapterListUpdateCallback(@NonNull RecyclerView.Adapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void onInserted(int position, int count) {
        mAdapter.notifyItemRangeInserted(position, count);
    }

    @Override
    public void onRemoved(int position, int count) {
        mAdapter.notifyItemRangeRemoved(position, count);
    }

    @Override
    public void onMoved(int fromPosition, int toPosition) {
        if (fromPosition == 0 || toPosition == 0) {
            mAdapter.notifyItemChanged(fromPosition);
            mAdapter.notifyItemChanged(toPosition);
        } else {
            mAdapter.notifyItemMoved(fromPosition, toPosition);
        }
    }

    @Override
    public void onChanged(int position, int count, Object payload) {
        mAdapter.notifyItemRangeChanged(position, count, payload);
    }
}