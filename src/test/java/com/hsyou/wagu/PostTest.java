package com.hsyou.wagu;

import com.hsyou.wagu.model.Account;
import com.hsyou.wagu.model.Comment;
import com.hsyou.wagu.model.Post;
import com.hsyou.wagu.repository.AccountRepository;
import com.hsyou.wagu.repository.CommentRepository;
import com.hsyou.wagu.repository.PostRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
/*
By default DataJpaTest replaces your DataSource with an embedded database but you don't have one.
So, if you want to test with MySQL, replace your test as follows:

@AutoConfigureTestDatabase(replace = NONE)

If you want to use an in-memory database for those tests, you need to add one to the test classpath. Add this to your gradle file
testCompile('com.h2database:h2')

 */
public class PostTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CommentRepository commentRepository;

    @After
    public void cleanup() {
        /**
         이후 테스트 코드에 영향을 끼치지 않기 위해
         테스트 메소드가 끝날때 마다 respository 전체 비우는 코드
         **/
        postRepository.deleteAll();
    }

    @Test
    @Rollback(false)
    public void 포스트등록하기(){

        /**
         * @DataJpaTest 에 보면 @Transactional 어노테이션으로 트랜잭션을 걸어놨기 때문에 아래 코드는
         * 실제 데이터베이스에 싱크되지 않고 롤백이 된다.
         *
         * hibernate는 필요할때만 쿼리를 실제 데이터베이스에 싱크하기 때문에
         * 아래 코드가 롤백될 것을 알고 쿼리를 실행하지 않음
         *
         * 이를 해결하려면 @Rollback(flase) 어노테이션을 테스트 메서드에 추가한다.
         */



        //Given
        Post post = Post.builder()
                .title("test")
                .contents("hello world")
                .build();

        //When

        Post resultPost = postRepository.save(post);

        //Then
        assertThat(resultPost.getId()).isNotNull();


        //When
        List<Post> posts = postRepository.findAll();

        //Then
        assertThat(posts.size()).isEqualTo(1);
        assertThat(posts).contains(resultPost);

        //When
        Page<Post> page = postRepository.findAll(PageRequest.of(0,10));

        //Then
        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getSize()).isEqualTo(10);
        assertThat(page.getNumberOfElements()).isEqualTo(1);

        //When
        page = postRepository.findByTitleContains("test", PageRequest.of(0, 10));
        //Then
        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getSize()).isEqualTo(10);
        assertThat(page.getNumberOfElements()).isEqualTo(1);



    }

    @Test
    @Rollback(false)
    public void 포스트에댓글등록하기() {
        //Given

        Post post = new Post();
        post.setTitle("test");
        post.setContents("hello world");

        Post resultPost = postRepository.save(post);

        Comment comment = Comment.builder()
                .contents("hello, im hs")
                .build();

        //When

        post.addComment(comment);
        //Then
        Optional<Post> byId = postRepository.findById(resultPost.getId());
        assertThat(byId.isPresent()).isEqualTo(true);
        assertThat(byId.get().getComments().contains(comment)).isEqualTo(true);
    }


    @Test
    @Rollback(false)
    public void 이러면어떻게될까(){
        //Given

        Post post1 = new Post();
        post1.setTitle("post1");
        post1.setContents("hello");


        Post post2 = new Post();
        post2.setTitle("post1");
        post2.setContents("hello");

        Comment comment2 = Comment.builder()
                .contents("hello, im hs2")
                .build();

        Post result = postRepository.save(post1);
        long id = result.getId();

        //When
        post2.addComment(comment2);

        //Then
        Optional<Post> optPost2 =postRepository.findById(id);
        assertThat(optPost2.get().getComments().size()).isEqualTo(0);


    }

    @Test
    @Rollback(false)
    public void 글등록후댓글등록(){

        //Given
        Account account1 = new Account();
        account1.setName("post_writer");


        Account account2 = new Account();
        account2.setName("comment_writer");


        Account postW = accountRepository.save(account1);
        Account commW = accountRepository.save(account2);

        //When

        Post post = new Post();
        post.setTitle("test");
        post.setContents("hello");
        post.setWriter(postW);

        Post newPo = postRepository.save(post);

        Comment comment = new Comment();
        comment.setContents("wow");
        comment.setPost(newPo);
        comment.setWriter(commW);

        Comment newC = commentRepository.save(comment);
        //Then
    }
}
