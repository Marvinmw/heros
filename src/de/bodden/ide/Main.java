package de.bodden.ide;

import java.util.Map;

import de.bodden.ide.problems.IFDSLocalInfoFlow;
import de.bodden.ide.solver.IFDSSolver;
import de.bodden.ide.template.JimpleBasedInterproceduralCFG;

import soot.Local;
import soot.PackManager;
import soot.Scene;
import soot.SceneTransformer;
import soot.SootMethod;
import soot.Transform;
import soot.Unit;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		PackManager.v().getPack("wjtp").add(new Transform("wjtp.ifds", new SceneTransformer() {
			protected void internalTransform(String phaseName, @SuppressWarnings("rawtypes") Map options) {

				IFDSTabulationProblem<Unit,Local,SootMethod,InterproceduralCFG<Unit,SootMethod>> problem = new IFDSLocalInfoFlow(new JimpleBasedInterproceduralCFG());
				
				IFDSSolver<Unit,Local,SootMethod,InterproceduralCFG<Unit,SootMethod>> solver = new IFDSSolver<Unit,Local,SootMethod,InterproceduralCFG<Unit,SootMethod>>(problem);	
				solver.solve();
				Unit ret = Scene.v().getMainMethod().getActiveBody().getUnits().getLast();
				for(Local l: solver.ifdsResultsAt(ret)) {
					System.err.println(l);
				}
			}
		}));
		
		soot.Main.main(args);
	}

}