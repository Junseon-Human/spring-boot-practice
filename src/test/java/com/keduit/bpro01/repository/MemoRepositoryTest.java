package com.keduit.bpro01.repository;

import com.keduit.bpro01.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTest {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass() {
        System.out.println("=======================" + memoRepository.getClass().getName());
    }

    @Test
    public void testInsertDumies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample...." + i).build();
            memoRepository.save(memo);
        });
    }

    @Test
    public void testSelect() {
        Long mno = 100L;

        Optional<Memo> result = memoRepository.findById(mno);
        System.out.println("===============================");

        if (result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

    @Transactional
    @Test
    public void testSelect2(){
        Long mno = 100L;
        Memo memo = memoRepository.getOne(mno);
        System.out.println("===========================");
        System.out.println(memo);
    }

    @Test
    public void testUpdate(){
        Memo memo = Memo.builder().mno(100L).memoText("update text").build();
        System.out.println(memoRepository.save(memo));
    }
    @Test
    public void testDelete() {
        Long mno = 100L;
        memoRepository.deleteById(mno);
    }

    @Test
    public void testPageDefault() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Memo> result = memoRepository.findAll(pageable);
        System.out.println(result);
    }

    @Test
    public void testPageDefault2() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Memo> result = memoRepository.findAll(pageable);
        System.out.println(result);
        System.out.println("--------------------------------");
        System.out.println("총 페이지 갯수: " + result.getTotalPages());
        System.out.println("전체 갯수: " + result.getTotalElements());
        System.out.println("현재 페이지 번호: " + result.getNumber());
        System.out.println("페이지당 데이터 갯수: " + result.hasNext());
        System.out.println("시작 페이지 여부: " + result.isFirst());
        System.out.println("--------------------------------------------");
        for (Memo memo : result.getContent()){
            System.out.println(memo);
        }
    }

    @Test
    public void testSort() {
        Sort sort1 = Sort.by("mno").descending();
        Pageable pageable = PageRequest.of(0, 10, sort1);
        Page<Memo> result = memoRepository.findAll(pageable);
        result.get().forEach(memo -> System.out.println(memo));
    }

    @Test
    public void testQueryMeth() {
        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);

        for(Memo memo: list) {
            System.out.println(memo);
        }
    }

    @Test
    public void testQueryMethodWithPagable() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Memo> result = memoRepository.findByMnoBetween(10L, 50L, pageable);
        result.get().forEach(memo -> System.out.println(memo));
    }

    @Test
    @Commit     // delete 의 경우 삭제 테스트 이후 자동으로 롤백되기때문에 commit 으로 실제 테이블 정보를 저장
    @Transactional
    public void testDeleteQueryMethod() {
        memoRepository.deleteMemoByMnoLessThan(10L);
    }


}
