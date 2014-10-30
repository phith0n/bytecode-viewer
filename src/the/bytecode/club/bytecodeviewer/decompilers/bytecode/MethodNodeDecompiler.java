package the.bytecode.club.bytecodeviewer.decompilers.bytecode;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TryCatchBlockNode;

import the.bytecode.club.bytecodeviewer.BytecodeViewer;
import the.bytecode.club.bytecodeviewer.decompilers.bytecode.TypeAndName;

/**
 * 
 * @author Konloch
 * @author Bibl
 *
 */

public class MethodNodeDecompiler {

    @SuppressWarnings("unused")
	public static PrefixedStringBuilder decompile(PrefixedStringBuilder sb, MethodNode m, ClassNode cn) {
		String package_ = null;
        String class_ = null;
        if (cn.name.contains("/")) {
            package_ = cn.name.substring(0, cn.name.lastIndexOf("/"));
            class_ = cn.name.substring(cn.name.lastIndexOf("/")+1);
        } else {
            class_ = cn.name;
        }
        
		String s = getAccessString(m.access);
		sb.append("     ");
		sb.append(s);
		if (s.length() > 0)
			sb.append(" ");
		
		System.out.println(m.name);
        if (m.name.equals("<init>")) {
            sb.append(class_);
        } else if (m.name.equals("<clinit>")) {
        } else {
            sb.append(m.name);
        }
		
        TypeAndName[] args = new TypeAndName[0];
        
        if (!m.name.equals("<clinit>")) {
            sb.append("(");
            
            final Type[] argTypes = Type.getArgumentTypes(m.desc);
            args = new TypeAndName[argTypes.length];
            
            for (int i = 0;i < argTypes.length; i++) {
                final Type type = argTypes[i];
                
                final TypeAndName tan = new TypeAndName();
                final String argName = "arg" + i;
                
                tan.name = argName;
                tan.type = type;
                
                args[i] = tan;
                
                sb.append(type.getClassName() + " " + argName + (i < argTypes.length-1 ? ", " : ""));
            }
            
            sb.append(")");
        }
        
		int amountOfThrows = m.exceptions.size();
		if (amountOfThrows > 0) {
			sb.append(" throws ");
			sb.append(m.exceptions.get(0));// exceptions is list<string>
			for (int i = 1; i < amountOfThrows; i++) {
				sb.append(", ");
				sb.append(m.exceptions.get(i));
			}
		}
		
		if (s.contains("abstract")) {
			sb.append(" {}"+BytecodeViewer.nl);
		} else {

			sb.append(" {");
			
			if (BytecodeViewer.viewer.debugHelpers.isSelected()) {
				if(m.name.equals("<clinit>"))
					sb.append(" // <clinit>");
				else if(m.name.equals("<init>"))
					sb.append(" // <init>");
			}
			
			sb.append(BytecodeViewer.nl);
			
			InstructionPrinter insnPrinter = new InstructionPrinter(m, args);
			for (Object o : m.tryCatchBlocks) {
				TryCatchBlockNode tcbn = (TryCatchBlockNode) o;
				sb.append("         ");
				sb.append("TryCatch: L");
				sb.append(insnPrinter.resolveLabel(tcbn.start));
				sb.append(" to L");
				sb.append(insnPrinter.resolveLabel(tcbn.end));
				sb.append(" handled by L");
				sb.append(insnPrinter.resolveLabel(tcbn.handler));
				sb.append(": ");
				if(tcbn.type != null)
					sb.append(tcbn.type);
				else
					sb.append("Type is null.");
				sb.append(BytecodeViewer.nl);
			}
			for (String insn : insnPrinter.createPrint()) {
				sb.append("         ");
				sb.append(insn);
				sb.append(BytecodeViewer.nl);
			}
			sb.append("     }"+BytecodeViewer.nl);
		}
		return sb;
	}
	
	private static String getAccessString(int access) {
		// public, protected, private, abstract, static,
		// final, synchronized, native & strictfp are permitted
		List<String> tokens = new ArrayList<String>();
		if ((access & Opcodes.ACC_PUBLIC) != 0)
			tokens.add("public");
		if ((access & Opcodes.ACC_PRIVATE) != 0)
			tokens.add("private");
		if ((access & Opcodes.ACC_PROTECTED) != 0)
			tokens.add("protected");
		if ((access & Opcodes.ACC_STATIC) != 0)
			tokens.add("static");
		if ((access & Opcodes.ACC_ABSTRACT) != 0)
			tokens.add("abstract");
		if ((access & Opcodes.ACC_FINAL) != 0)
			tokens.add("final");
		if ((access & Opcodes.ACC_SYNCHRONIZED) != 0)
			tokens.add("synchronized");
		if ((access & Opcodes.ACC_NATIVE) != 0)
			tokens.add("native");
		if ((access & Opcodes.ACC_STRICT) != 0)
			tokens.add("strictfp");
		if ((access & Opcodes.ACC_BRIDGE) != 0)
			tokens.add("bridge");
		if ((access & Opcodes.ACC_VARARGS) != 0)
			tokens.add("varargs");
		if (tokens.size() == 0)
			return "";
		// hackery delimeters
		StringBuilder sb = new StringBuilder(tokens.get(0));
		for (int i = 1; i < tokens.size(); i++) {
			sb.append(" ");
			sb.append(tokens.get(i));
		}
		return sb.toString();
	}
}