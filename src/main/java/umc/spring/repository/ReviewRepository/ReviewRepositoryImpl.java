package umc.spring.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
// import umc.spring.domain.QReview;
// import umc.spring.domain.QStore;

import java.util.List;

/*
@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QStore store = QStore.store;
    private final QReview review = QReview.review;

    @Override
    public List<String> getReviewsWithBooleanBuilder(Long storeId) {
        return jpaQueryFactory
                .select(review.body)
                .from(review)
                .join(store).on(store.id.eq(storeId))
                .orderBy(review.createdAt.desc())
                .fetch();
    }
}

 */
