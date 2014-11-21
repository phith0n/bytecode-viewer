package the.bytecode.club.bytecodeviewer.obfuscators;

import org.objectweb.asm.tree.ClassNode;

import the.bytecode.club.bytecodeviewer.BytecodeViewer;
import the.bytecode.club.bytecodeviewer.api.ASMUtil_OLD;

public class RenameClasses extends JavaObfuscator {
	
	@Override
	public void obfuscate() {
		int stringLength = getStringLength();
		
		System.out.println("Obfuscating");
		for(ClassNode c : BytecodeViewer.getLoadedClasses()) {
			String newName = generateUniqueName(stringLength);
			ASMUtil_OLD.renameClassNode(c.name, newName);
			c.name = newName;
		}
		
		System.out.println("Obfuscated");
	}

}
