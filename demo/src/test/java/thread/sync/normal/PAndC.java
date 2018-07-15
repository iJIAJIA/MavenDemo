package thread.sync.normal;

public class PAndC {
	
	
	
	public static void main(String[] args) {
			
//		List<Object> basket = new LinkedList<Object>();
//		int count = 10 ;
//		Thread prd = new Thread(new Producer(basket, count));
//		Thread con = new Thread(new Consumer(basket));
//		prd.start();
//		con.start();
		Depot depot = new Depot(300);
		Producer prd = new Producer(depot);
		Consumer con = new Consumer(depot);
		prd.produce(300);
		prd.produce(200);
		con.consumer(1);
		prd.produce(1);
//		con.consumer(100);
//		con.consumer(150);
//		con.consumer(150);
	}
	
	static class Depot{
		int capacity;
		int size;
		
		public Depot(int capacity) {
			this.capacity = capacity;
		}
		
		public synchronized void produce(int val) {
			int left = val;
			
			while(left > 0) {
//				if(size == capacity) {
//				这里不能用if,每次生产者拿到同步锁时,不能保证当前的库存就一定不等于仓库的容量
//				因为生产者消费者用的是同一个锁,有可能是另外一个相同生产者进行了notify();那么假如
//				此时当前库存等于仓库的容量,被唤醒并且拿到锁的生产者还是会执行以下加库存的操作
				while(size == capacity) {
					try {
						wait();
					} catch (InterruptedException e) {
						
					}
				}
				int inc = ( (capacity - size) > left)? left: (capacity - size);
				size += inc;
				left -= inc;
				System.out.printf("%s produce(%3d) --> left=%3d, inc=%3d, size=%3d\n", 
						Thread.currentThread().getName(), val, left, inc, size);
				notifyAll();
			}
		}
		
		public synchronized void consume(int val) {
			int left = val;
			while(left > 0) {
				if(size == 0) {
					try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				int dec = (size < left)? size: left;
 				size -= dec;
				left -= dec;
				System.out.printf("%s consume(%3d) <-- left=%3d, dec=%3d, size=%3d\n", 
						 Thread.currentThread().getName(), val, left, dec, size);
				notifyAll();
			}
		}
	}
	
	static class Producer extends Thread{
		
		private Depot depot;
		
		public Producer(Depot depot) {
			this.depot = depot;
		}
		
		
		
		public void produce(final int val) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					depot.produce(val);
				}
			}).start();
		}
	}
	
	static class Consumer {
		
		private Depot depot;
		
		public Consumer(Depot depot) {
			this.depot = depot;
		}
		
		public void consumer(final int val) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					depot.consume(val);
				}
			}).start();
		}
	}
	
//	static class Producer implements Runnable{
//		
//		List<Object> basket;
//		
//		int basketCount;
//		
//		public Producer(List<Object> basket,int baksetCount) {
//			this.basket = basket;
//			this.basketCount = baksetCount;
//		}
//		
//		public void putEggs() {
//			synchronized (PAndC.class) {
//				while(true) {
//					if(basket.size() == basketCount	) {
//						try {
//							System.out.println("暂停生产");
//							PAndC.class.notify();
//							PAndC.class.wait();
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//					System.out.println("放置前容量: " + basket.size());
//					basket.add(new Object());
//					System.out.println("放置了一个鸡蛋,当前容量: " + basket.size());
////					while(basket.size() == basketCount) {
//////					不再生产
////						System.out.println("暂停生产");
////					}
//				}
//			}
//		}
//		
//		@Override
//		public void run() {
//			putEggs();
//		}
//	}
//	
//	static class Consumer implements Runnable{
//		
//		List<Object> basket;
//		
//		public Consumer(List<Object> basket) {
//			this.basket = basket;
//		}
//		
//		public void takeEggs() {
//			synchronized (PAndC.class) {
//				while(true) {
//					if(basket.size() == 0) {
//						try {
//							System.out.println("暂停消费");
//							PAndC.class.notify();
//							PAndC.class.wait();
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//					System.out.println("消费前容量: " + basket.size());
//					basket.remove(0);
//					System.out.println("消费了一个鸡蛋,当前容量: " + basket.size());
//				}
//			}
//		}
//
//		@Override
//		public void run() {
//			takeEggs();
//		}
//	}
}
