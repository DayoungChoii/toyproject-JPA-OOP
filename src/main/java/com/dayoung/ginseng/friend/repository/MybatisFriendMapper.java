package com.dayoung.ginseng.friend.repository;

import com.dayoung.ginseng.friend.domain.FriendVo;
import com.dayoung.ginseng.member.domain.MemberVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MybatisFriendMapper extends FriendRepository{
    @Override
    List<MemberVo> findMember(FriendVo friendId);

    @Override
    void requestFriend(FriendVo friendVo);

    @Override
    List<FriendVo> findMyFriendsByStatus(FriendVo friendVo);

    @Override
    List<FriendVo> findRequestedFriend(FriendVo friendVo);

    @Override
    int acceptFriend(FriendVo myId);
}
