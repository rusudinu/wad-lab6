package com.rusudinu.wad_lab6.second.repository;

import com.rusudinu.wad_lab6.second.document.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductDocumentRepository extends ElasticsearchRepository<ProductDocument, String> {
}
