package the.bytecode.club.bytecodeviewer.obfuscators;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import the.bytecode.club.bytecodeviewer.BytecodeViewer;
import the.bytecode.club.bytecodeviewer.api.ASMUtil_OLD;

public class RenameFields extends JavaObfuscator {
	
	@Override
	public void obfuscate() {
		int stringLength = getStringLength();
		
		System.out.println("Obfuscating");
		for(ClassNode c : BytecodeViewer.getLoadedClasses()) {
			for(Object o : c.fields.toArray()) {
				FieldNode f = (FieldNode)o;
				String newName = generateUniqueName(stringLength);
				ASMUtil_OLD.renameFieldNode(c.name,f.name,f.desc, null, newName, null);
				f.name = newName;
			}
		}
		
		System.out.println("Obfuscated");
	}

}
