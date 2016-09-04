package com.skjanyou.recycle.services;

public interface StartupService {
	public void start();
	/**这里返回一个线程，在程序结束后可以用钩子进行一些善后处理**/
	public Thread end();
}
