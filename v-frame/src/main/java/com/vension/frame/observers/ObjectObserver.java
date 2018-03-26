package com.vension.frame.observers;

import java.util.List;
import java.util.Stack;

/**
 * Created by Administrator on 2017/3/22.
 * @author Vension ==> hqw@kewaimiao.com
 * activity和fragment的堆栈式管理 观察者
 */
public abstract class ObjectObserver<T> {

	private Stack<T> _Observer = new Stack<>();

	/**添加T进栈*/
	public boolean registObjectObserver(T t) {
		if(!_Observer.contains(t)) {
			return _Observer.add(t);
		}
		return false;
	}

	/**将T移除栈*/
	public boolean unregistObjectObserver(T t) {

		if(_Observer.contains(t)) {
			return _Observer.remove(t);
		}
		return false;
	}

	public boolean unregistObjectObserver(int position) {
		return _Observer.remove(position) != null;
	}

	/**获取T*/
	public T getObserver(Class<? extends T> _Class) {

		for (T _t : _Observer) {
			if (_t.getClass().getName().equals(_Class.getName())) {
				return _t;
			}
		}
		return null;
	}


	/**获取当前T*/
	public T getCurrentObserver() {
		if (_Observer.size() > 0){
			return _Observer.lastElement();
		}
		return null;
	}



	/**清除所有*/
	public synchronized void clear() {
		if(!_Observer.isEmpty()) {
			_Observer.clear();
		}
	}


	public int size() {
		return _Observer.size();
	}

	public List<T> getObserverList() {
		return _Observer;
	}

	public abstract void notifyDataSetChanged();

	public abstract void notifyDataSetChanged(T t);

	public abstract void notifyDataSetChanged(Class<? extends T> _Class);
}

