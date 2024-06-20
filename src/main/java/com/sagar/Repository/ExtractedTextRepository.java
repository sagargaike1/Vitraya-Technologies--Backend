package com.sagar.Repository;

import com.sagar.Model.ExtractedText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtractedTextRepository extends JpaRepository<ExtractedText ,Long> {
}
