package spoon;

import java.util.List;


import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.support.reflect.code.CtInvocationImpl;
import spoon.support.reflect.declaration.CtMethodImpl;

public class MethodExecutionProcessor extends AbstractProcessor<CtMethodImpl> {

	@Override
	public void process(CtMethodImpl ctMethod) {
		System.out.println("typeReference: " + ctMethod.getType());
		// List<CtElement> elements = ctMethod.getElements(new
		// AbstractFilter<CtElement>(CtElement.class) {
		// @Override
		// public boolean matches(CtElement ctElement) {
		// // System.out.println("|-- " + ctElement);
		// // System.out.println("\\-- is CtInvocationImpl: " + (ctElement
		// instanceof CtInvocationImpl));
		// // System.out.println("\\-- is CtNewClassImpl: " + (ctElement
		// instanceof CtNewClassImpl));
		// // System.out.println("\\-- is CtConstructorCallImpl: " + (ctElement
		// instanceof CtConstructorCallImpl));
		// return ctElement instanceof CtInvocationImpl;
		// }
		// });
		// List<CtExecutableReference> calls = new ArrayList<>();
		// for (CtElement element : elements) {
		// CtInvocationImpl invocation = (CtInvocationImpl) element;
		// calls.add(invocation.getExecutable());
		// System.out.println(">>>>>>>>>>>>>>>>>>>> ");
		// // 方法名
		// System.out.println("element: " + invocation);
		// System.out.println("getReferencedTypes:" +
		// invocation.getReferencedTypes());
		// for (CtTypeReference ref : invocation.getReferencedTypes()) {
		// // TODO 筛选目标目录下面的方法
		// //
		// ref.getPackage().toString().startsWith("com.ule.shoppingOrderEjb");
		// System.out.println("package: " + ref.getPackage());
		// // 方法的提供方
		// System.out.println("reference: " + ref.toString());
		// }
		// }
		// System.out.println("method name : " + ctMethod.getSimpleName());
		List<CtElement> elements = ctMethod
				.getElements(new AbstractFilter<CtElement>(CtElement.class) {
					@Override
					public boolean matches(CtElement ctElement) {
						// 筛选方法体里面调用的方法
						return ctElement instanceof CtInvocationImpl;
					}
				});
		for (CtElement element : elements) {
			CtInvocationImpl invocation = (CtInvocationImpl) element;
			// System.out.println(">>>>>>>>>>>>>>>>>>>> ");
			// 方法名
			// System.out.println("element: " + invocation);
			CtExecutableReference executable = invocation.getExecutable();
			System.out.println("methodName: " + ctMethod.getSimpleName());
			System.out.println("executable.getDeclaringType(): "
					+ executable.getDeclaringType());
			// TODO 如何实现类编译
			System.out.println("executable.getActualMethod(): "
					+ executable.getActualMethod());
			System.out.println("executable.getType(): " + executable.getType());
			if (executable.getDeclaringType().toString()
					.startsWith("com.ule.shoppingOrderEjb.client")) {
			}
			// for (CtTypeReference ref : invocation.getReferencedTypes()) {
			// // TODO 筛选目标目录下面的方法
			// //
			// ref.getPackage().toString().startsWith("com.ule.shoppingOrderEjb");
			// // 方法的提供方
			// if(ref.toString().startsWith("com.ule.shoppingOrderEjb.client")){
			// System.out.println(" ================== ");
			// System.out.println("classname: " + paramE.getSimpleName());
			// System.out.println("methodName: " + ctMethod.getSimpleName());
			// System.out.println("invocation: " + invocation);
			// // invocation.getExecutable() :
			// com.myShoppingOrderWeb.web.action.searchOrder.BuyerOrderSearchAction#findOrderSysBookMap(com.ule.shoppingOrderEjb.client.IConstantEjbClient)
			// System.out.println("invocation.getExecutable(): " +
			// invocation.getExecutable());
			// // invocation.getTarget():
			// System.out.println("invocation.getTarget(): " +
			// invocation.getTarget());
			// System.out.println("reference: " + ref.toString());
			// }
			// }
		}
	}

}
