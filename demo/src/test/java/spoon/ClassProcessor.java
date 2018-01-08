package spoon;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.support.reflect.code.CtInvocationImpl;
import spoon.support.reflect.declaration.CtClassImpl;

@SuppressWarnings("rawtypes")
public class ClassProcessor extends AbstractProcessor<CtClassImpl> {

	private List<CHClass> recordList;
	
	
	public ClassProcessor(List<CHClass> recordList) {
		super();
		this.recordList = recordList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public void process(CtClassImpl paramE) {
		CHClass chClass = null;
		Set<CtMethod> allMethods = (Set<CtMethod>) paramE.getMethods();
		if (allMethods != null) {
			List<CHMethod> methodList = null;
			for (CtMethod ctMethod : allMethods) {
//				System.out.println("method name : " + ctMethod.getSimpleName());
				List<CtElement> elements = ctMethod
						.getElements(new AbstractFilter<CtElement>(
								CtElement.class) {
							@Override
							public boolean matches(CtElement ctElement) {
//								筛选方法体里面调用的方法
								return ctElement instanceof CtInvocationImpl;
							}
						});
				CHMethod chMethod = null;
				for (CtElement element : elements) {
					CtInvocationImpl invocation = (CtInvocationImpl) element;
					CtExecutableReference executable = invocation.getExecutable();
					List<CHMethod> executableList = null;
					chMethod = new CHMethod();
					chMethod.setDecalaredClassName(paramE.getSimpleName());
					chMethod.setName(ctMethod.getSimpleName());
					if(executable.getDeclaringType().toString().startsWith("com.ule.shoppingOrderEjb.client")){
						if(chClass == null){
							chClass = new CHClass();
							chClass.setName(paramE.getSimpleName());
						}
						if(methodList == null){
							methodList = new ArrayList<CHMethod>();
							chClass.setMethod(methodList);
						}
						methodList.add(chMethod);
						if(executableList == null){
							executableList = new ArrayList<CHMethod>();
							chMethod.setBodyCallMethods(executableList);
						}
						CHMethod executableMethod = new CHMethod();
						executableList.add(executableMethod);
						executableMethod.setName(executable.getSimpleName());
						executableMethod.setDecalaredClassName(executable.getDeclaringType().toString());
//						executableMethod.setRemark(executable.getDeclaration().toString());
					}
				}
			}
		}
		if(chClass != null){
			recordList.add(chClass);
		}
	}

}
