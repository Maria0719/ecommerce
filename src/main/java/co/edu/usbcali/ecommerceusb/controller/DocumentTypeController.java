package co.edu.usbcali.ecommerceusb.controller;

import co.edu.usbcali.ecommerceusb.dto.CreateDocumentTypeRequest;
import co.edu.usbcali.ecommerceusb.dto.DocumentTypeResponse;
import co.edu.usbcali.ecommerceusb.dto.UpdateDocumentTypeRequest;
import co.edu.usbcali.ecommerceusb.service.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/document-type")
public class DocumentTypeController {

    @Autowired
    private DocumentTypeService documentTypeService;

    @GetMapping("/all")
    public List<DocumentTypeResponse> getAll() {
        return documentTypeService.getDocumentTypes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentTypeResponse> getById(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(documentTypeService.getDocumentTypeById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DocumentTypeResponse> createDocumentType(@RequestBody CreateDocumentTypeRequest createDocumentTypeRequest) throws Exception {
        return new ResponseEntity<>(documentTypeService.createDocumentType(createDocumentTypeRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentTypeResponse> updateDocumentType(@PathVariable Integer id,
                                                                   @RequestBody UpdateDocumentTypeRequest updateDocumentTypeRequest) throws Exception {
        return new ResponseEntity<>(documentTypeService.updateDocumentType(id, updateDocumentTypeRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumentType(@PathVariable Integer id) throws Exception {
        documentTypeService.deleteDocumentType(id);
        return ResponseEntity.noContent().build();
    }
}
