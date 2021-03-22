package pl.coderslab.imageManager.mock;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import pl.coderslab.imageManager.image.Image;
import pl.coderslab.imageManager.image.ImageRepository;

import java.util.*;

public class MockDatabase implements ImageRepository {
    private static final Map<Long, Image> images = new HashMap<>();

    @Override
    public Image getByName(String name) {
        Set<Map.Entry<Long, Image>> entrySet = images.entrySet();
        Iterator<Map.Entry<Long, Image>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, Image> next = iterator.next();
            String imageName = next
                    .getValue()
                    .getName();
            if (imageName.equals(name)) {
                return next.getValue();
            }
        }
        return null;
    }

    @Override
    public List<Image> findAll() {
        List<Image> imagesList = new ArrayList<>();
        Set<Map.Entry<Long, Image>> entrySet = images.entrySet();
        Iterator<Map.Entry<Long, Image>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, Image> next = iterator.next();
            Image image = next.getValue();
            imagesList.add(image);
        }
        return imagesList;
    }

    @Override
    public List<Image> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Image> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Image> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return images.size();
    }

    @Override
    public void deleteById(Long id) {
        images.remove(id);
    }

    @Override
    public void delete(Image image) {

    }

    @Override
    public void deleteAll(Iterable<? extends Image> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Image save(Image image) {
        Long nextId = image.getId();
        if (nextId == null){
            nextId = Long.parseLong(String.valueOf(images.size()+1));
        }
        image.setId(nextId);
        images.put(nextId, image);
        return images.get(nextId);
    }

    @Override
    public <S extends Image> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Image> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Image> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<Image> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Image getOne(Long id) {
        return images.get(id);
    }

    @Override
    public <S extends Image> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Image> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Image> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Image> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Image> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Image> boolean exists(Example<S> example) {
        return false;
    }
}
