package com.KoreaIT.ksh.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.KoreaIT.ksh.demo.dto.Chat;
import com.KoreaIT.ksh.demo.dto.ChatRoom;
import com.KoreaIT.ksh.demo.dto.Chat_User;
import com.KoreaIT.ksh.demo.dto.Chat.MessageType;


@Mapper
public interface ChatRepository {

	public List<ChatRoom> getRooms();

    public ChatRoom getRoomById(int id);

    public void createChatRoom(String roomName, int memberId);

    public void addUser(int roomId, int memberId);

    public void delUser(int roomId, int memberId);

    public String getUserName(int roomId, int memberId);

    public List<String> getUserList(int roomId);

    public void saveChat(MessageType type, int roomId, String sender, int memberId, String message, String time);

	public int getLastInsertId();

	public Chat_User getChat_UserByRoomIdAndMemberId(int roomId, int memberId);

	public List<Chat> getChatHistory(int roomId);
}