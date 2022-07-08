package com.skybird.photoimagedemo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.print.PrintAttributes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Date：2022/7/6
 * Describe:
 */
public class Utils {

    public static File saveBitmapForPdf(Bitmap bitmaps, String appDir, String name) {
        PdfDocument doc = new PdfDocument();
        int pageWidth = PrintAttributes.MediaSize.ISO_A4.getWidthMils() * 72 / 1000;

        float scale = (float) pageWidth / (float) bitmaps.getWidth();
        int pageHeight = (int) (bitmaps.getHeight() * scale);

        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        PdfDocument.PageInfo newPage = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight,0).create();
        PdfDocument.Page page = doc.startPage(newPage);
        Canvas canvas = page.getCanvas();
        canvas.drawBitmap(bitmaps, matrix, paint);
        doc.finishPage(page);
        File file = new File(appDir, name);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            doc.writeTo(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            doc.close();
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
