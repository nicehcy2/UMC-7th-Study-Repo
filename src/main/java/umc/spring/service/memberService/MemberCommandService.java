package umc.spring.service.memberService;

import umc.spring.domain.Member;
import umc.spring.dto.MemberRequestDTO;

public interface MemberCommandService {
    Member joinMember(MemberRequestDTO.JoinDto request);
}
