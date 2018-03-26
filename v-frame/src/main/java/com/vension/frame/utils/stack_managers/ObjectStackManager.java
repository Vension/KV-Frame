package com.vension.frame.utils.stack_managers;

import java.lang.ref.WeakReference;
import java.util.Stack;

/**
 * @author ：Created by vension on 2018/1/19.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

public abstract class ObjectStackManager<T> {

	private Stack<WeakReference<T>> _Stack;


	/**
	 * 添加T进栈
	 * @param t
	 */
	public boolean addObjectInStack(WeakReference<T> t) {
		if (_Stack == null) {
			_Stack = new Stack<>();
		}
		if(!_Stack.contains(t)) {
			return _Stack.add(t);
		}
		return false;
	}

	/**
	 * 将T移除栈
	 */
	public boolean removeObjectOutStack(WeakReference<T> t) {
		if (_Stack != null) {
			if(_Stack.contains(t)) {
				return _Stack.remove(t);
			}
		}
		return false;
	}

	public boolean removeObjectOutStack(int position) {
		return _Stack.remove(position) != null;
	}


	/***
	 * 获取栈顶T（堆栈中最后一个压入的）
	 *
	 * @return T
	 */
	public T getTopStack() {
		return _Stack.lastElement().get();
	}

	/***
	 * 通过class 获取栈顶T
	 *
	 * @param _Class
	 * @return T
	 */
	public T getTackByClass(Class<? extends T> _Class) {
		for (WeakReference<T> _t : _Stack) {
			if (_t.get().getClass().getName().equals(_Class.getName())) {
				return _t.get();
			}
		}
		return null;
	}



	/***
	 * 栈中Activity的数
	 *
	 * @return Activity的数
	 */
	public int stackSize() {
		return _Stack.size();
	}


	/***
	 * 获得栈
	 *
	 * @return
	 */
	public Stack<WeakReference<T>> getStack() {
		return _Stack;
	}

	/**清除所有*/
	public synchronized void clear() {
		if(!_Stack.isEmpty()) {
			_Stack.clear();
		}
	}

}
