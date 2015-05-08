package edu.ncf.virus;

import java.io.File;

public class Preprocessor {
	File virus_folder = null;

	public Preprocessor(String path) {
		virus_folder = new File(path);
		if (!virus_folder.isDirectory())
			System.out.println("must provide directory");
	}

	public void process(File f) {
		// unzip
		// remove pe header
		// write out
	}

	public void unzip() {
		File[] viruses = virus_folder.listFiles();
	}
}
