package com.google.zxing.client.android.encode;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

public final class MyEncoder extends Thread {
    private final String contents;
    private final int pixelResolution;
    private final BarcodeFormat format;

    public MyEncoder(String contents, int pixelResolution, BarcodeFormat format) {
        this.contents = contents;
        this.pixelResolution = pixelResolution;
        this.format = format;
    }

    public Bitmap getBitmap() {
        Bitmap bitmap = null;

        try {
            bitmap = QRCodeEncoder.encodeAsBitmap(contents, format, pixelResolution, pixelResolution);
        } catch (WriterException e) {
            Log.e("QR Code Error", "Could not encode barcode", e);

        } catch (IllegalArgumentException e) {
            Log.e("QR Code Error", "Could not encode barcode", e);
        }

        return bitmap;
    }
}