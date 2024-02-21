package com.prayer.live.test;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-02-18 12:48
 **/
public interface HelloSPI {
	void sayHello();
}

class IHello implements HelloSPI{

	@Override
	public void sayHello() {

	}
}

class THello implements HelloSPI{

	@Override
	public void sayHello() {

	}
}
