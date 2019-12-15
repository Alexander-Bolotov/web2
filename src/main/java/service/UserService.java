package service;

import model.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class UserService {

    private static UserService instance;

    private UserService(Map<Long, User> dataBase, AtomicLong maxId, Map<Long, User> authMap) {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService(dataBase, maxId, authMap);
        }
        return instance;
    }

    /* хранилище данных */
    private static Map<Long, User> dataBase = Collections.synchronizedMap(new HashMap<>());
    /* счетчик id */
    private static AtomicLong maxId = new AtomicLong(0);
    /* список авторизованных пользователей */
    private static Map<Long, User> authMap = Collections.synchronizedMap(new HashMap<>());

    public List<User> getAllUsers() {
        return new ArrayList<>(dataBase.values());
    }

    public User getUserById(Long id) {
        return dataBase.get(id);
    }

    public boolean addUser(User user) {
        if (isExistsThisUser(user)) {
            return false;
        } else {
            user.setId(maxId.incrementAndGet());
            dataBase.put(maxId.get(), user);
            return true;
        }
    }

    public void deleteAllUser() {
        dataBase.clear();
    }

    public boolean isExistsThisUser(User user) {
        return getIdByUser(user) != null;
    }

    public List<User> getAllAuth() {
        return new ArrayList<>(authMap.values());
    }

    public boolean authUser(User user) {
        if (isExistsThisUser(user)) {
            user.setId(getIdByUser(user));
            authMap.put(user.getId(), user);
            return true;
        }
        return false;
    }

    public void logoutAllUsers() {
        authMap.clear();
    }

    public boolean isUserAuthById(Long id) {
        return authMap.containsKey(id);
    }

    public Long getIdByUser(User user) {
        Long id = null;
        Set<Map.Entry<Long, User>> entrySet = dataBase.entrySet();
        for (Map.Entry<Long, User> pair : entrySet) {
            if (user.compareTo(pair.getValue()) == 0) {
                id = pair.getKey();
            }
        }
        return id;
    }
}

