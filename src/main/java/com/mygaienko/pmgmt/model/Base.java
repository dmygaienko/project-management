package com.mygaienko.pmgmt.model;

import java.util.List;

public class Base<T extends Base<T>> {
	public static List<? extends Base> staticList;
}
