package edu.ncf.virus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.boris.pecoff4j.PE;
import org.boris.pecoff4j.SectionTable;
import org.boris.pecoff4j.io.PEParser;

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
		PE pe = PEParser.parse(f);
		File sterilized = new File(writeto.getAbsolutePath() + "/" + f.getName());
		FileOutputStream fos = new FileOutputStream(sterilized);
		SectionTable st = pe.getSectionTable();
		int sections = st.getNumberOfSections();
		for (int i = 0; i < sections; i++) {
			int characteristics = st.getHeader(i).getCharacteristics();
			String name = st.getHeader(i).getName().toLowerCase();
			if ((characteristics & 0xFF) == 0x20) {
				System.out.println(name);
				System.out.println(Integer.toHexString(characteristics));
				fos.write(st.getSection(i).getData());
			}
		}
		fos.flush();
		fos.close();
	}
}
