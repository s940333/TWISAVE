package com.example.tien.myapplication;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;

/*
 Created by roi on 2016/8/8.
 Android Toast
 */
public class Toast {
    //類別常數
    /* 短時間訊息*/
    public static final int SHORT = android.widget.Toast.LENGTH_SHORT;

    /*
     * 長時間訊息
     */
    public static final int LONG = android.widget.Toast.LENGTH_LONG;

    /* 類別位置
     * @author magiclen
     */
    public static enum Position {
        CENTER, CENTER_BOTTOM, CENTER_TOP, CENTER_LEFT, CENTER_RIGHT, LEFT_TOP, RIGHT_TOP, LEFT_BOTTOM, RIGHT_BOTTOM;
    }

    /*
     * 調整Toast與螢幕邊界的距離，數值愈大距離愈遠
     */
    private static final int offsetFactor = 12;

    // -----類別變數-----
    /*
     * 儲存Context的參考
     */
    private static Context context;

    /*
     * 儲存Toast的參考
     */
    private static android.widget.Toast toast;

    /*
     * 儲存Toast的位置
     */
    private static Position toastPosition = Position.CENTER_BOTTOM;

    /*
     * 儲存X邊界
     */
    private static int offsetX;

    /*
     * 儲存Y邊界
     */
    private static int offsetY;

    /*
     * 儲存使用者使用的ToastView
     */
    private static ToastView toastView;

    /*
     * 設定Context給Toast使用，建議在Activity onCreate的時候都指定一次，<br/>
     * 避免參考到的Context已經被釋放掉，或是變數被清空
     *
     * @param context
     */
    public static void setContext(final Context context) {
        Toast.context = context;
        useDefaultOffset();
    }

    /*
     * 指定ToastView，這個設定只有在重新呼叫showToast方法後才可以看到結果
     *
     * @param toastView
     *            傳入ToastView物件
     */
    public static void setToastView(final ToastView toastView) {
        Toast.toastView = toastView;
        toast = null;
    }

    /*
     * 設定Toast的位置，這個設定只有在重新呼叫showToast方法後才可以看到結果
     *
     * @param position
     *            傳入Toast的位置
     */
    public static void setPosition(final Position position) {
        if (position != null) {
            Toast.toastPosition = position;
        } else {
            Toast.toastPosition = Position.CENTER_BOTTOM;
        }
        useDefaultOffset();
    }

    /*
     * 設定Toast的位置，這個設定只有在重新呼叫showToast方法後才可以看到結果
     *
     * @param position
     *            傳入Toast的位置
     * @param offsetX
     *            傳入X位移量
     * @param offsetY
     *            傳入Y位移量
     */
    public static void setPosition(final Position position, final int offsetX, final int offsetY) {
        if (position != null) {
            Toast.toastPosition = position;
        } else {
            Toast.toastPosition = Position.CENTER_BOTTOM;
        }
        setOffset(offsetX, offsetY);
    }

    /*
     * 使用預設的位移
     */
    private static void useDefaultOffset() {
        final DisplayMetrics dm = context.getResources().getDisplayMetrics();
        setOffset(dm.widthPixels / offsetFactor, dm.heightPixels / offsetFactor);
    }

    /*
     * 設定氣泡訊息的位移
     *
     * @param offsetX
     *            傳入X位移量
     * @param offsetY
     *            傳入Y位移量
     */
    private static void setOffset(final int offsetX, final int offsetY) {
        Toast.offsetX = offsetX;
        Toast.offsetY = offsetY;
    }

    /*
     * 顯示氣泡訊息(短時間)
     *
     * @param text
     *            傳入訊息內容
     */
    public static void showToast(final String text) {
        showToast(text, android.widget.Toast.LENGTH_SHORT);
    }

    /*
     * 建立Toast物件
     *
     * @param text
     *            傳入訊息內容
     * @param duration
     *            傳入訊息維持的時間
     * @return 傳回Toast物件
     */
    private static android.widget.Toast makeText(final String text, final int duration) {
        if (toastView != null) {
            final android.widget.Toast toast = new android.widget.Toast(context);
            toastView.setText(text);
            toast.setDuration(duration);
            toast.setView(toastView);
            return toast;
        } else {
            return android.widget.Toast.makeText(context, text,android.widget.Toast.LENGTH_SHORT);
        }
    }

    /*
     * 顯示氣泡訊息
     *
     * @param text
     *            傳入訊息內容
     * @param duration
     *            傳入訊息維持的時間
     */
    public static void showToast(final String text, final int duration) {
        if (toast == null) {
            // 如果Toast物件不存在，就用makeText方法建立一個新的Toast
            toast = makeText(text, duration);
        } else {
            // 如果Toast物件存在，就重設它的持續時間和訊息文字
            toast.setDuration(duration);
            if (toastView == null) {
                toast.setText(text);
            } else {
                toastView.setText(text);
            }
        }

        // 設定氣泡訊息的位置
        int gravity = 0, offsetX = 0, offsetY = 0;
        switch (toastPosition) {
            case CENTER:
                gravity = Gravity.CENTER;
                break;
            case CENTER_BOTTOM:
                gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
                offsetY = Toast.offsetY;
                break;
            case CENTER_TOP:
                gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                offsetY = Toast.offsetY;
                break;
            case CENTER_LEFT:
                gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
                offsetX = Toast.offsetX;
                break;
            case CENTER_RIGHT:
                gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
                offsetX = Toast.offsetX;
                break;
            case LEFT_TOP:
                gravity = Gravity.LEFT | Gravity.TOP;
                offsetX = Toast.offsetX;
                offsetY = Toast.offsetY;
                break;
            case LEFT_BOTTOM:
                gravity = Gravity.LEFT | Gravity.BOTTOM;
                offsetX = Toast.offsetX;
                offsetY = Toast.offsetY;
                break;
            case RIGHT_TOP:
                gravity = Gravity.RIGHT | Gravity.TOP;
                offsetX = Toast.offsetX;
                offsetY = Toast.offsetY;
                break;
            case RIGHT_BOTTOM:
                gravity = Gravity.RIGHT | Gravity.BOTTOM;
                offsetX = Toast.offsetX;
                offsetY = Toast.offsetY;
                break;
        }
        toast.setGravity(gravity, offsetX, offsetY);
        // 顯示出氣泡訊息
        toast.show();
    }

    /*
     * 顯示氣泡訊息(短時間)
     *
     * @param textId
     *            傳入String Resoure的ID，或是整數數字
     */
    public static void showToast(final int textId) {
        showToast(textId, android.widget.Toast.LENGTH_SHORT);
    }

    /*
     * 顯示氣泡訊息
     *
     * @param textId
     *            傳入String Resoure的ID，或是整數數字
     * @param duration
     *            傳入訊息維持的時間
     */
    public static void showToast(final int textId, final int duration) {
        String text;
        try {
            // 嘗試取得String Resource
            text = context.getResources().getString(textId);
        } catch (final Exception ex) {
            // 若String Resource取得失敗，則直接使用整數作為氣泡訊息
            text = String.valueOf(textId);
        }
        showToast(text, duration);
    }

    /*
     * 顯示氣泡訊息(短時間)
     *
     * @param decimalText
     *            傳入整數或是浮點數數字作為訊息內容
     */
    public static void showToast(final double decimalText) {
        showToast(decimalText, android.widget.Toast.LENGTH_SHORT);
    }

    /*
     * 顯示氣泡訊息
     *
     * @param decimalText
     *            傳入整數或是浮點數數字作為訊息內容
     * @param duration
     */
    public static void showToast(final double decimalText, final int duration) {
        if (decimalText == Math.round(decimalText)) {
            // 如果傳入的數是整數
            showToast(String.valueOf((int) decimalText), duration);
        } else {
            // 如果傳入的數不是整數
            showToast(String.valueOf(decimalText), duration);
        }
    }
}
