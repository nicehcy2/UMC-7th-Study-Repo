package umc.spring.repository.MissionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
// import umc.spring.domain.QMember;
// import umc.spring.domain.QMission;

import java.util.List;

/*
@Repository
@RequiredArgsConstructor
public class MissionRepositoryImpl implements MissionRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QMission mission = QMission.mission;
    private final QMember member = QMember.member;


    @Override
    public List<String> getMissionNameWithBooleanBuilder(Long memberId, int currentPageIndex) {

        int pageSize = 10;
        int offset = (currentPageIndex - 1) * pageSize;

        return jpaQueryFactory
                .select(mission.name)
                .from(mission)
                .join(member).on(member.id.eq(memberId))
                .where(mission.status.eq("진행중")).or(mission.status.eq("진행완료"))
                .limit(pageSize)
                .offset(offset)
                .fetch();
    }

    @Override
    public List<String> getMissionNameOnTargetUserWithBooleanBuilder(Long memberId, int location, int currentPageIndex) {

        int pageSize = 10;
        int offset = (currentPageIndex - 1) * pageSize;

        return jpaQueryFactory
                .select(mission.name)
                .from(mission)
                .join(member).on(member.id.eq(memberId))
                .where(mission.location.eq(location))
                .limit(pageSize)
                .offset(offset)
                .fetch();
    }
}
*/
