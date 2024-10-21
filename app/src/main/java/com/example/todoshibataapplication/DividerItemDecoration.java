package com.example.todoshibataapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

// DividerItemDecorationはRecyclerViewのアイテム間に区切り線を描画するためのクラス
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private final Drawable divider;

    // コンストラクタでContextを受け取り、区切り線のDrawableを取得
    public DividerItemDecoration(Context context) {
        divider = ContextCompat.getDrawable(context, R.drawable.divider);
    }

    // onDrawメソッドで区切り線を描画する
    @Override
    public void onDraw(Canvas c, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        // RecyclerViewの全ての子ビューをループ
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            // 区切り線の上端と下端の位置を計算
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + divider.getIntrinsicHeight();

            // 区切り線の描画範囲を設定
            divider.setBounds(left, top, right, bottom);
            // 区切り線を描画
            divider.draw(c);
        }
    }
}

