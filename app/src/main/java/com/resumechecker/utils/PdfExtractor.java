package com.resumechecker.utils;

import android.content.Context;
import android.net.Uri;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.InputStream;

public class PdfExtractor {

    /**
     * Extracts plain text from a PDF file given its content URI.
     */
    public static String extractText(Context context, Uri pdfUri) throws Exception {
        InputStream inputStream = context.getContentResolver().openInputStream(pdfUri);
        if (inputStream == null) throw new Exception("Cannot open PDF file.");

        PdfReader reader = new PdfReader(inputStream);
        PdfDocument pdfDoc = new PdfDocument(reader);

        StringBuilder sb = new StringBuilder();
        int pages = pdfDoc.getNumberOfPages();

        for (int i = 1; i <= pages; i++) {
            String pageText = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(i));
            sb.append(pageText).append("\n");
        }

        pdfDoc.close();
        reader.close();
        inputStream.close();

        return TextUtils.cleanText(sb.toString());
    }
}
