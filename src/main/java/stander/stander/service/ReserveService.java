package stander.stander.service;
import stander.stander.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class ReserveService {

    private ReserveRepository reserveRepository;
}
