package dompoo.blog;

import dompoo.blog.domain.Comment;
import dompoo.blog.domain.Member;
import dompoo.blog.domain.Writing;
import dompoo.blog.etc.security.SecurityConfig;
import dompoo.blog.repository.CommentRepository;
import dompoo.blog.repository.MemberRepository;
import dompoo.blog.repository.writing.WritingRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@Import(SecurityConfig.class)
@Slf4j
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Configuration
	@Profile("local")
	@RequiredArgsConstructor
	public class localDataInit {
		private final CommentRepository commentRepository;
		private final WritingRepository writingRepository;
		private final MemberRepository memberRepository;

		@PostConstruct
		public void init() {
			log.info("웹 테스트 데이터 init");

			Member member1 = memberRepository.save(Member.builder()
					.username("윤동주")
					.password("password1")
					.build());

			Member member2 = memberRepository.save(Member.builder()
					.username("나태주")
					.password("password2")
					.build());

			Member member3 = memberRepository.save(Member.builder()
					.username("asd")
					.password("asd")
					.build());

			for (int i = 1; i <= 100; i++) {
				writingRepository.save(Writing.builder()
						.title(i + "번 글 제목")
						.content(i + "번 글 내용")
						.member(member3)
						.build());
			}

			Writing writing1 = writingRepository.save(Writing.builder()
					.title("서시")
					.content("죽는 날까지 하늘을 우러러\n" +
							"한 점 부끄럼이 없기를,\n" +
							"잎새에 이는 바람에도 \n" +
							"나는 괴로워했다.\n" +
							"별을 노래하는 마음으로 \n" +
							"모든 죽어가는 것을 사랑해야지\n" +
							"그리고 나한테 주어진 길을 \n" +
							"걸어가야겠다.\n\n" +
							"오늘 밤에도 별이 바람에 스치운다.")
					.member(member1)
					.build());

			Writing writing2 = writingRepository.save(Writing.builder()
					.title("내가 좋아하는 사람")
					.content("내가 좋아하는 사람은\n" +
							"슬퍼할 일을 마땅히 슬퍼하고\n" +
							"괴로워할 일을 마땅히\n" +
							"괴로워하는 사람\n\n" +
							"남의 앞에 섰을 때\n" +
							"교만하지 않고\n" +
							"남의 뒤에 섰을 때\n" +
							"비굴하지 않은 사람\n" +
							"\n" +
							"내가 좋아하는 사람은\n" +
							"미워할 것을 마땅히 미워하고\n" +
							"사랑할 것을 마땅히 사랑하는\n" +
							"그저 보통의 사람")
					.member(member2)
					.build());

			commentRepository.save(Comment.builder()
					.content("잘 봤습니다.")
					.writing(writing1)
					.member(member2)
					.build());

			commentRepository.save(Comment.builder()
					.content("잘 봤습니다.22")
					.writing(writing2)
					.member(member1)
					.build());
		}
	}

}
