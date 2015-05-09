package edu.ncf.virus;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		System.out.println("Helo viruzssz! prepair 2 DIE");

		Sterilizer s = new Sterilizer();
		// s.sterilizeFolder("viruses");

		s.sterilizeFolder("win_xp_benign");

	}
}
