package cn.hua.domain;

import java.util.List;

public class Friend {
	private String kind;	//分组
	private List<Users> list;	//分组下好友集合
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public List<Users> getList() {
		return list;
	}
	public void setList(List<Users> list) {
		this.list = list;
	}
	
}
