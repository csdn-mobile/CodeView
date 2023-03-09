package net.csdn.codeview.demo;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CodeAdapter());

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                // AD请求
                if (newState == SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof LinearLayoutManager) {
                        int firstItem = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                        int lastItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                        if (firstItem <= lastItem) {
                            for (int i = 0; i <= lastItem - firstItem; i++) {
                                if (i < 0) {
                                    continue;
                                }
                                if (i >= recyclerView.getChildCount()) {
                                    break;
                                }
                                View child = recyclerView.getChildAt(i);
                                traverseViewGroup(i, child);
                            }
                        }
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    /**
     * 遍历viewGroup
     */
    public int traverseViewGroup(int position, View view) {
        int viewCount = 0;
        if (null == view) {
            return 0;
        }
        if (view instanceof ViewGroup) {
            //遍历ViewGroup,是子view加1，是ViewGroup递归调用
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View child = ((ViewGroup) view).getChildAt(i);
                Log.e("RecyclerViewActivity", "===position:" + position + "========" + child.getClass().getName());
                if (child instanceof ViewGroup) {
                    viewCount += traverseViewGroup(position, ((ViewGroup) view).getChildAt(i));
                } else {
                    viewCount++;
                }
            }
        } else {
            viewCount++;
        }
        return viewCount;
    }
}
