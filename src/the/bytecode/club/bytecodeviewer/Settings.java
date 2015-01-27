package the.bytecode.club.bytecodeviewer;

import javax.swing.JFrame;

import me.konloch.kontainer.io.DiskReader;
import me.konloch.kontainer.io.DiskWriter;

/**
 * Used to handle loading/saving the GUI (options).
 * 
 * @author Konloch
 *
 */

public class Settings {

	public static void saveGUI() {
		try {
			DiskWriter.replaceFile(BytecodeViewer.settingsName, "", false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.rbr.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.rsy.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.din.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.dc4.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.das.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.hes.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.hdc.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.dgs.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.ner.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.den.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.rgn.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.bto.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.nns.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.uto.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.udv.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.rer.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.fdi.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.asc.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.decodeenumswitch.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.sugarenums.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.decodestringswitch.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.arrayiter.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.collectioniter.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.innerclasses.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.removeboilerplate.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.removeinnerclasssynthetics.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.decodelambdas.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.hidebridgemethods.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.liftconstructorinit.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.removedeadmethods.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.removebadgenerics.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.sugarasserts.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.sugarboxing.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.showversion.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.decodefinally.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.tidymonitors.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.lenient.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.dumpclasspath.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.comments.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.forcetopsort.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.forcetopsortaggress.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.stringbuffer.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.stringbuilder.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.silent.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.recover.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.eclipse.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.override.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.showinferrable.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.aexagg.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.forcecondpropagate.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.hideutf.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.hidelongstrings.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.commentmonitor.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.allowcorrecting.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.labelledblocks.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.j14classobj.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.hidelangimports.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.recoverytypeclash.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.recoverytypehints.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.forceturningifs.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.forloopaggcapture.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.forceexceptionprune.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.chckbxmntmShowDebugLine.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.chckbxmntmSimplifyMemberReferences.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.mnMergeVariables.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.chckbxmntmNewCheckItem_1.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.chckbxmntmNewCheckItem_2.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.chckbxmntmNewCheckItem_3.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.chckbxmntmNewCheckItem_4.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.chckbxmntmNewCheckItem_5.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.chckbxmntmNewCheckItem_6.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.chckbxmntmNewCheckItem_7.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.chckbxmntmNewCheckItem_8.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.chckbxmntmNewCheckItem_9.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.chckbxmntmNewCheckItem_10.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.chckbxmntmNewCheckItem_11.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.chckbxmntmAppendBrackets.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.debugHelpers.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, "deprecated", false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.chckbxmntmNewCheckItem_12.isSelected()), false);
			 if(BytecodeViewer.viewer.panelGroup1.isSelected(BytecodeViewer.viewer.panel1None.getModel()))
				 DiskWriter.writeNewLine(BytecodeViewer.settingsName, "0", false);
			 else if(BytecodeViewer.viewer.panelGroup1.isSelected(BytecodeViewer.viewer.panel1Proc.getModel()))
				 DiskWriter.writeNewLine(BytecodeViewer.settingsName, "1", false);
			 else if(BytecodeViewer.viewer.panelGroup1.isSelected(BytecodeViewer.viewer.panel1CFR.getModel()))
				 DiskWriter.writeNewLine(BytecodeViewer.settingsName, "2", false);
			 else if(BytecodeViewer.viewer.panelGroup1.isSelected(BytecodeViewer.viewer.panel1Fern.getModel()))
				 DiskWriter.writeNewLine(BytecodeViewer.settingsName, "3", false);
			 else if(BytecodeViewer.viewer.panelGroup1.isSelected(BytecodeViewer.viewer.panel1Bytecode.getModel()))
				 DiskWriter.writeNewLine(BytecodeViewer.settingsName, "4", false);
			 else if(BytecodeViewer.viewer.panelGroup1.isSelected(BytecodeViewer.viewer.panel1Hexcode.getModel()))
				 DiskWriter.writeNewLine(BytecodeViewer.settingsName, "5", false);
			 else if(BytecodeViewer.viewer.panelGroup1.isSelected(BytecodeViewer.viewer.panel1Smali.getModel()))
				 DiskWriter.writeNewLine(BytecodeViewer.settingsName, "6", false);
			   
			 if(BytecodeViewer.viewer.panelGroup2.isSelected(BytecodeViewer.viewer.panel2None.getModel()))
				 DiskWriter.writeNewLine(BytecodeViewer.settingsName, "0", false);
			 else if(BytecodeViewer.viewer.panelGroup2.isSelected(BytecodeViewer.viewer.panel2Proc.getModel()))
				 DiskWriter.writeNewLine(BytecodeViewer.settingsName, "1", false);
			 else if(BytecodeViewer.viewer.panelGroup2.isSelected(BytecodeViewer.viewer.panel2CFR.getModel()))
				 DiskWriter.writeNewLine(BytecodeViewer.settingsName, "2", false);
			 else if(BytecodeViewer.viewer.panelGroup2.isSelected(BytecodeViewer.viewer.panel2Fern.getModel()))
				 DiskWriter.writeNewLine(BytecodeViewer.settingsName, "3", false);
			 else if(BytecodeViewer.viewer.panelGroup2.isSelected(BytecodeViewer.viewer.panel2Bytecode.getModel()))
				 DiskWriter.writeNewLine(BytecodeViewer.settingsName, "4", false);
			 else if(BytecodeViewer.viewer.panelGroup2.isSelected(BytecodeViewer.viewer.panel2Hexcode.getModel()))
				 DiskWriter.writeNewLine(BytecodeViewer.settingsName, "5", false);
			 else if(BytecodeViewer.viewer.panelGroup2.isSelected(BytecodeViewer.viewer.panel2Smali.getModel()))
				 DiskWriter.writeNewLine(BytecodeViewer.settingsName, "6", false);
			 
			 if(BytecodeViewer.viewer.panelGroup3.isSelected(BytecodeViewer.viewer.panel3None.getModel()))
				 DiskWriter.writeNewLine(BytecodeViewer.settingsName, "0", false);
			 else if(BytecodeViewer.viewer.panelGroup3.isSelected(BytecodeViewer.viewer.panel3Proc.getModel()))
				 DiskWriter.writeNewLine(BytecodeViewer.settingsName, "1", false);
			 else if(BytecodeViewer.viewer.panelGroup3.isSelected(BytecodeViewer.viewer.panel3CFR.getModel()))
				 DiskWriter.writeNewLine(BytecodeViewer.settingsName, "2", false);
			 else if(BytecodeViewer.viewer.panelGroup3.isSelected(BytecodeViewer.viewer.panel3Fern.getModel()))
				 DiskWriter.writeNewLine(BytecodeViewer.settingsName, "3", false);
			 else if(BytecodeViewer.viewer.panelGroup3.isSelected(BytecodeViewer.viewer.panel3Bytecode.getModel()))
				 DiskWriter.writeNewLine(BytecodeViewer.settingsName, "4", false);
			 else if(BytecodeViewer.viewer.panelGroup3.isSelected(BytecodeViewer.viewer.panel3Hexcode.getModel()))
				 DiskWriter.writeNewLine(BytecodeViewer.settingsName, "5", false);
			 else if(BytecodeViewer.viewer.panelGroup3.isSelected(BytecodeViewer.viewer.panel3Smali.getModel()))
				 DiskWriter.writeNewLine(BytecodeViewer.settingsName, "6", false);
			 
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.refreshOnChange.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.isMaximized), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.autoCompileSmali.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, String.valueOf(BytecodeViewer.viewer.autoCompileOnRefresh.isSelected()), false);
			DiskWriter.writeNewLine(BytecodeViewer.settingsName, BytecodeViewer.lastDirectory, false);
		} catch(Exception e) {
			new the.bytecode.club.bytecodeviewer.api.ExceptionUI(e);
		}
	}
	
	public static void loadGUI() { //utilizes the Disk Reader's caching system.
		try {
			BytecodeViewer.viewer.rbr.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 1, true)));
			BytecodeViewer.viewer.rsy.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 2, false)));
			BytecodeViewer.viewer.din.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 3, false)));
			BytecodeViewer.viewer.dc4.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 4, false)));
			BytecodeViewer.viewer.das.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 5, false)));
			BytecodeViewer.viewer.hes.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 6, false)));
			BytecodeViewer.viewer.hdc.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 7, false)));
			BytecodeViewer.viewer.dgs.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 8, false)));
			BytecodeViewer.viewer.ner.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 9, false)));
			BytecodeViewer.viewer.den.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 10, false)));
			BytecodeViewer.viewer.rgn.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 11, false)));
			BytecodeViewer.viewer.bto.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 12, false)));
			BytecodeViewer.viewer.nns.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 13, false)));
			BytecodeViewer.viewer.uto.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 14, false)));
			BytecodeViewer.viewer.udv.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 15, false)));
			BytecodeViewer.viewer.rer.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 16, false)));
			BytecodeViewer.viewer.fdi.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 17, false)));
			BytecodeViewer.viewer.asc.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 18, false)));
			BytecodeViewer.viewer.decodeenumswitch.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 19, false)));
			BytecodeViewer.viewer.sugarenums.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 20, false)));
			BytecodeViewer.viewer.decodestringswitch.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 21, false)));
			BytecodeViewer.viewer.arrayiter.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 22, false)));
			BytecodeViewer.viewer.collectioniter.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 23, false)));
			BytecodeViewer.viewer.innerclasses.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 24, false)));
			BytecodeViewer.viewer.removeboilerplate.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 25, false)));
			BytecodeViewer.viewer.removeinnerclasssynthetics.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 26, false)));
			BytecodeViewer.viewer.decodelambdas.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 27, false)));
			BytecodeViewer.viewer.hidebridgemethods.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 28, false)));
			BytecodeViewer.viewer.liftconstructorinit.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 29, false)));
			BytecodeViewer.viewer.removedeadmethods.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 30, false)));
			BytecodeViewer.viewer.removebadgenerics.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 31, false)));
			BytecodeViewer.viewer.sugarasserts.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 32, false)));
			BytecodeViewer.viewer.sugarboxing.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 33, false)));
			BytecodeViewer.viewer.showversion.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 34, false)));
			BytecodeViewer.viewer.decodefinally.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 35, false)));
			BytecodeViewer.viewer.tidymonitors.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 36, false)));
			BytecodeViewer.viewer.lenient.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 37, false)));
			BytecodeViewer.viewer.dumpclasspath.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 38, false)));
			BytecodeViewer.viewer.comments.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 39, false)));
			BytecodeViewer.viewer.forcetopsort.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 40, false)));
			BytecodeViewer.viewer.forcetopsortaggress.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 41, false)));
			BytecodeViewer.viewer.stringbuffer.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 42, false)));
			BytecodeViewer.viewer.stringbuilder.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 43, false)));
			BytecodeViewer.viewer.silent.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 44, false)));
			BytecodeViewer.viewer.recover.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 45, false)));
			BytecodeViewer.viewer.eclipse.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 46, false)));
			BytecodeViewer.viewer.override.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 47, false)));
			BytecodeViewer.viewer.showinferrable.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 48, false)));
			BytecodeViewer.viewer.aexagg.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 49, false)));
			BytecodeViewer.viewer.forcecondpropagate.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 50, false)));
			BytecodeViewer.viewer.hideutf.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 51, false)));
			BytecodeViewer.viewer.hidelongstrings.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 52, false)));
			BytecodeViewer.viewer.commentmonitor.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 53, false)));
			BytecodeViewer.viewer.allowcorrecting.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 54, false)));
			BytecodeViewer.viewer.labelledblocks.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 55, false)));
			BytecodeViewer.viewer.j14classobj.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 56, false)));
			BytecodeViewer.viewer.hidelangimports.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 57, false)));
			BytecodeViewer.viewer.recoverytypeclash.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 58, false)));
			BytecodeViewer.viewer.recoverytypehints.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 59, false)));
			BytecodeViewer.viewer.forceturningifs.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 60, false)));
			BytecodeViewer.viewer.forloopaggcapture.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 61, false)));
			BytecodeViewer.viewer.forceexceptionprune.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 62, false)));
			BytecodeViewer.viewer.chckbxmntmShowDebugLine.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 63, false)));
			BytecodeViewer.viewer.chckbxmntmSimplifyMemberReferences.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 64, false)));
			BytecodeViewer.viewer.mnMergeVariables.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 65, false)));
			BytecodeViewer.viewer.chckbxmntmNewCheckItem_1.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 66, false)));
			BytecodeViewer.viewer.chckbxmntmNewCheckItem_2.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 67, false)));
			BytecodeViewer.viewer.chckbxmntmNewCheckItem_3.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 68, false)));
			BytecodeViewer.viewer.chckbxmntmNewCheckItem_4.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 69, false)));
			BytecodeViewer.viewer.chckbxmntmNewCheckItem_5.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 70, false)));
			BytecodeViewer.viewer.chckbxmntmNewCheckItem_6.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 71, false)));
			BytecodeViewer.viewer.chckbxmntmNewCheckItem_7.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 72, false)));
			BytecodeViewer.viewer.chckbxmntmNewCheckItem_8.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 73, false)));
			BytecodeViewer.viewer.chckbxmntmNewCheckItem_9.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 74, false)));
			BytecodeViewer.viewer.chckbxmntmNewCheckItem_10.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 75, false)));
			BytecodeViewer.viewer.chckbxmntmNewCheckItem_11.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 76, false)));
			BytecodeViewer.viewer.chckbxmntmAppendBrackets.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 77, false)));
			BytecodeViewer.viewer.debugHelpers.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 78, false)));
			//79 is deprecated
			BytecodeViewer.viewer.chckbxmntmNewCheckItem_12.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 80, false)));
			int decompiler = Integer.parseInt(DiskReader.loadString(BytecodeViewer.settingsName, 81, false));
			if(decompiler == 0)
				BytecodeViewer.viewer.panelGroup1.setSelected(BytecodeViewer.viewer.panel1None.getModel(), true);
			else if(decompiler == 1)
				BytecodeViewer.viewer.panelGroup1.setSelected(BytecodeViewer.viewer.panel1Proc.getModel(), true);
			else if(decompiler == 2)
				BytecodeViewer.viewer.panelGroup1.setSelected(BytecodeViewer.viewer.panel1CFR.getModel(), true);
			else if(decompiler == 3)
				BytecodeViewer.viewer.panelGroup1.setSelected(BytecodeViewer.viewer.panel1Fern.getModel(), true);
			else if(decompiler == 4)
				BytecodeViewer.viewer.panelGroup1.setSelected(BytecodeViewer.viewer.panel1Bytecode.getModel(), true);
			else if(decompiler == 5)
				BytecodeViewer.viewer.panelGroup1.setSelected(BytecodeViewer.viewer.panel1Hexcode.getModel(), true);
			else if(decompiler == 6)
				BytecodeViewer.viewer.panelGroup1.setSelected(BytecodeViewer.viewer.panel1Smali.getModel(), true);

			decompiler = Integer.parseInt(DiskReader.loadString(BytecodeViewer.settingsName, 82, false));
			if(decompiler == 0)
				BytecodeViewer.viewer.panelGroup2.setSelected(BytecodeViewer.viewer.panel2None.getModel(), true);
			else if(decompiler == 1)
				BytecodeViewer.viewer.panelGroup2.setSelected(BytecodeViewer.viewer.panel2Proc.getModel(), true);
			else if(decompiler == 2)
				BytecodeViewer.viewer.panelGroup2.setSelected(BytecodeViewer.viewer.panel2CFR.getModel(), true);
			else if(decompiler == 3)
				BytecodeViewer.viewer.panelGroup2.setSelected(BytecodeViewer.viewer.panel2Fern.getModel(), true);
			else if(decompiler == 4)
				BytecodeViewer.viewer.panelGroup2.setSelected(BytecodeViewer.viewer.panel2Bytecode.getModel(), true);
			else if(decompiler == 5)
				BytecodeViewer.viewer.panelGroup2.setSelected(BytecodeViewer.viewer.panel2Hexcode.getModel(), true);
			else if(decompiler == 6)
				BytecodeViewer.viewer.panelGroup2.setSelected(BytecodeViewer.viewer.panel2Smali.getModel(), true);
	
			decompiler = Integer.parseInt(DiskReader.loadString(BytecodeViewer.settingsName, 83, false));
			if(decompiler == 0)
				BytecodeViewer.viewer.panelGroup3.setSelected(BytecodeViewer.viewer.panel3None.getModel(), true);
			else if(decompiler == 1)
				BytecodeViewer.viewer.panelGroup3.setSelected(BytecodeViewer.viewer.panel3Proc.getModel(), true);
			else if(decompiler == 2)
				BytecodeViewer.viewer.panelGroup3.setSelected(BytecodeViewer.viewer.panel3CFR.getModel(), true);
			else if(decompiler == 3)
				BytecodeViewer.viewer.panelGroup3.setSelected(BytecodeViewer.viewer.panel3Fern.getModel(), true);
			else if(decompiler == 4)
				BytecodeViewer.viewer.panelGroup3.setSelected(BytecodeViewer.viewer.panel3Bytecode.getModel(), true);
			else if(decompiler == 5)
				BytecodeViewer.viewer.panelGroup3.setSelected(BytecodeViewer.viewer.panel3Hexcode.getModel(), true);
			else if(decompiler == 6)
				BytecodeViewer.viewer.panelGroup3.setSelected(BytecodeViewer.viewer.panel3Smali.getModel(), true);
			
			BytecodeViewer.viewer.refreshOnChange.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 84, false)));
			
			boolean bool = Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 85, false));
			if(bool) {
				BytecodeViewer.viewer.setExtendedState(JFrame.MAXIMIZED_BOTH);
				BytecodeViewer.viewer.isMaximized = true;
			}
			BytecodeViewer.viewer.autoCompileSmali.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 86, false)));
			BytecodeViewer.viewer.autoCompileOnRefresh.setSelected(Boolean.parseBoolean(DiskReader.loadString(BytecodeViewer.settingsName, 87, false)));
			BytecodeViewer.lastDirectory = DiskReader.loadString(BytecodeViewer.settingsName, 88, false);
		} catch(Exception e) {
			//ignore because errors are expected, first start up and outdated settings.
		}
	}

}