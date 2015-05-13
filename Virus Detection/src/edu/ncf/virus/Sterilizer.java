package edu.ncf.virus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.github.katjahahn.parser.PEData;
import com.github.katjahahn.parser.PELoader;
import com.github.katjahahn.parser.sections.SectionHeader;
import com.github.katjahahn.parser.sections.SectionTable;

public class Sterilizer {
	File virus_folder = null;

	public void sterilizeFolder(String path) throws IOException {
		virus_folder = new File(path);
		if (!virus_folder.isDirectory())
			System.out.println("must provide directory");
		File sterilized_folder = new File(path + "_sterilized");
		sterilized_folder.mkdir();

		File[] files = virus_folder.listFiles();
		double i = 0;
		double l = files.length;
		for (File f : files) {
			if (f.getName().startsWith("."))
				continue;
			try {
				sterilizeVirus(f, sterilized_folder);
			} catch (Exception e) {
				System.out.println("Problem sterilizing: " + f.getAbsolutePath());
				e.printStackTrace();
				// System.exit(0);
			}
			i++;
			if (i % 100 == 0)
				System.out.println((100 * i / l) + "% percent complete");
		}
		System.out.println("Done.");
	}

	public void sterilizeVirus(File f, File writeto) throws IOException {
		File sterilized = new File(writeto.getAbsolutePath() + "/" + f.getName());
		FileOutputStream fos = new FileOutputStream(sterilized);
		try {
			FileInputStream fis = new FileInputStream(f);
			PEData pe = PELoader.loadPE(f);
			SectionTable st = pe.getSectionTable();
			int sections = st.getNumberOfSections();
			for (int i = 1; i <= sections; i++) {
				String name = st.getSectionHeader(i).getName().toLowerCase();
				if (name.equals(".data") || name.contains("text") || name.contains("code") || name.equals("data")) {
					SectionHeader s = st.getSectionHeader(i);
					byte[] section = new byte[(int) s.getAlignedSizeOfRaw()];
					fis.read(section, (int) s.getAlignedPointerToRaw(), (int) s.getAlignedSizeOfRaw());
					fos.write(section);
				}
			}
			fis.close();
		} catch (Exception e) {
			FileInputStream fis = new FileInputStream(f);
			int b;
			while ((b = fis.read()) != -1)
				fos.write(b);
			fis.close();
		}
		fos.flush();
		fos.close();
	}
}
