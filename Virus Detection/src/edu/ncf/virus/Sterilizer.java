package edu.ncf.virus;

import java.io.File;

public class Sterilizer {
	File virus_folder = null;

	public Sterilizer(String path) {
		virus_folder = new File(path);
		if (!virus_folder.isDirectory())
			System.out.println("must provide directory");
	}

	public void process(File f) {
		// remove pe header
		// write out
	}

}
