package cn.hua.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import cn.hua.dao.FriendDao;
import cn.hua.dao.LoginStateDao;
import cn.hua.dao.MessageDao;
import cn.hua.dao.PhotoDao;
import cn.hua.dao.SetDao;
import cn.hua.dao.UserDao;
import cn.hua.dao.impl.FriendDaoImpl;
import cn.hua.dao.impl.LoginStateDaoImpl;
import cn.hua.dao.impl.MessageDaoImpl;
import cn.hua.dao.impl.PhotoDaoImpl;
import cn.hua.dao.impl.SetDaoImpl;
import cn.hua.dao.impl.UserDaoImpl;
import cn.hua.domain.Friend;
import cn.hua.domain.Message;
import cn.hua.domain.Photo;
import cn.hua.domain.Users;
import cn.hua.service.BusinessService;
//业务层，没什么好说明的
public class BusinessServiceImpl implements BusinessService {
	private PhotoDao photoDao = new PhotoDaoImpl();
	private UserDao userDao = new UserDaoImpl();
	private FriendDao friendDao = new FriendDaoImpl();
	private LoginStateDao lsDao = new LoginStateDaoImpl();
	private MessageDao messageDao = new MessageDaoImpl();
	private SetDao setDao = new SetDaoImpl();
	public boolean addPhoto(Photo p){
		try {
			if(photoDao.add(p)>0){
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public Photo findPhoto(String id){
		try {
			return photoDao.find(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<Photo> getSysPhoto(){
		try {
			return photoDao.getSysPhoto();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public boolean addUser(Users user) {
		try {
			if(userDao.add(user)>0){
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public Users findUser(String id) {
		try {
			return userDao.find(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public List<Users> getAllUser() {
		try {
			return userDao.getAllUser();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public Users isExistUser(String type, String value) {
		try {
			return userDao.isExist(type, value);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public Users verifyUP(String username, String password) {
		try {
			return userDao.verifyUP(username, password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public int totalUsers() {
		try {
			return userDao.totalUsers();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public boolean addFriend(String myId, String fId, String kind) {
		try {
			if(friendDao.add(myId, fId, kind)>0){
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public boolean delFriend(String myId, String fId) {
		try {
			if(friendDao.del(myId, fId)>0){
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public int updateFriend(String myId, String fId, String kind) {
		try {
			return friendDao.update(myId, fId, kind);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public List<Friend> getFriends(String myId) {
		try {
			return friendDao.getFriends(myId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public int totalFriends() throws SQLException {
		try {
			return friendDao.totalFriends();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public List getKind(String id) {
		try {
			return friendDao.getKind(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public boolean addLoginState(String id) {
		try {
			if(lsDao.add(id)>0){
				return true;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return false;
	}
	@Override
	public boolean delLoginState() {
		try {
			if(lsDao.del()>0){
				return true;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return false;
	}
	@Override
	public boolean findLoginState(String id) {
		try {
			if(lsDao.find(id)>0){
				return true;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return false;
	}
	@Override
	public boolean updateLoginState(String id) {
		try {
			if(lsDao.update(id)>0){
				return true;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return false;
	}
	@Override
	public boolean addMessage(String id, String fid, String content) {
		try {
			int i = messageDao.add(id, fid, content);
			if(i>0){
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public List<Message> getMessage(String id, String fid) {
		try {
			return messageDao.getMessage(id, fid);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public Map<String, Object> getAllMessage(String id) {
		try {
			return messageDao.getAllMessage(id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public boolean updateUser(Users user) {
		try {
			if(userDao.update(user)>0){
				return true;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return false;
	}
	@Override
	public String getSound(String id) {
		try {
			return setDao.getSound(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public boolean setSound(String id, String sid) {
		try {
			if(setDao.setSound(id, sid)>0){
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public String getPicture(String id) {
		try {
			return setDao.getPicture(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public boolean setPicture(String id, String pid) {
		try {
			if(setDao.setPicture(id, pid)>0){
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public String getPS(String id) {
		try {
			return userDao.getPS(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public boolean isExistFriend(String id, String fid) {
		try {
			if(friendDao.isExist(id, fid)>0){
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
