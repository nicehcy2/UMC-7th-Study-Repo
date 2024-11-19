package umc.spring.service.memberService;

import umc.spring.domain.Member;
import umc.spring.dto.MemberRequestDTO;
import umc.spring.dto.MemberResponseDTO;

import java.util.List;

public interface MemberCommandService {
    Member joinMember(MemberRequestDTO.JoinDto request);
    Boolean isMember(String value);
    MemberResponseDTO.MyReviewListResponseDTO getMyReviews(Long memberId, Integer page);
}
