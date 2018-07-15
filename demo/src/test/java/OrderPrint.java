
public class OrderPrint {
	
	public static void main(String[] args) {
		final Resource resource = new Resource();
		for (int i = 1; i <= 5; i++) {
			Thread t = new Thread() {
				public void run() {
					for (int j = 0; j < 2; j++) {
						try {
							Thread.sleep(50);
							resource.add();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						resource.print();
					}
				}
			};
			t.start();
		}

	}
	
	static class Resource{
		
		private int count;
		
		private volatile boolean hasNewOne;
		
		private Object lock = new Object();
		
		public void add(){
			while(hasNewOne){
				try {
					Thread.currentThread().wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			synchronized (lock) {
				if(!hasNewOne){
					count++;
					hasNewOne = true;
				}
			}
		}
		
		public void print(){
			while(!hasNewOne){
				try {
					Thread.currentThread().wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			synchronized (lock) {
				if(hasNewOne){
					System.out.println("count:" + count);
					hasNewOne=false;
				}
			}
		}
	}
}
