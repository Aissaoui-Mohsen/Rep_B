package com.neolians.common.utils;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class PdfUtils {
	public static String getContent(File pdfFile) throws IOException {
		try (PDDocument document = Loader.loadPDF(pdfFile)) {

			document.getClass();

			if (!document.isEncrypted()) {

				final PDFTextStripperByArea stripper = new PDFTextStripperByArea();
				stripper.setSortByPosition(true);

				final PDFTextStripper tStripper = new PDFTextStripper();

				return tStripper.getText(document);

			}

		}
		return "";
	}
}
