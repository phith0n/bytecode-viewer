package the.bytecode.club.bytecodeviewer.obfuscators;

import java.util.ArrayList;

import the.bytecode.club.bytecodeviewer.BytecodeViewer;
import the.bytecode.club.bytecodeviewer.MiscUtils;

public abstract class JavaObfuscator extends Thread {

	@Override
	public void run() {
		System.out.println("mibbzz is gay");
		BytecodeViewer.viewer.setIcon(true);
		BytecodeViewer.runningObfuscation = true;
		obfuscate();
		BytecodeViewer.runningObfuscation = false;
		BytecodeViewer.viewer.setIcon(false);
	}

	public int getStringLength() {
		if (BytecodeViewer.viewer.obfuscatorGroup
				.isSelected(BytecodeViewer.viewer.strongObf.getModel())) {
			return MAX_STRING_LENGTH;
		} else { // if(BytecodeViewer.viewer.obfuscatorGroup.isSelected(BytecodeViewer.viewer.lightObf.getModel()))
					// {
			return MIN_STRING_LENGTH;
		}
	}

	public static int MAX_STRING_LENGTH = 250;
	public static int MIN_STRING_LENGTH = 20;
	private ArrayList<String> names = new ArrayList<String>();

	protected String generateUniqueName(int length) {
		boolean found = false;
		String name = "";
		while (!found) {
			String nameTry = MiscUtils.randomString(1) + MiscUtils.randomStringNum(length - 1);
			if (!names.contains(nameTry)) {
				names.add(nameTry);
				name = nameTry;
				found = true;
			}
		}
		return name;
	}

	public abstract void obfuscate();
}
