package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.entity.ResetPassword;
import ra.model.repository.ResetPasswordRepository;
import ra.model.service.ResetPasswordService;
@Service
public class ResetPasswordServiceImp implements ResetPasswordService {
    @Autowired
    private ResetPasswordRepository resetPasswordRepository;
    @Override
    public ResetPassword saveOrUpdate(ResetPassword resetPassword) {
        return resetPasswordRepository.save(resetPassword);
    }

    @Override
    public ResetPassword getLastTokenByUserId(int userId) {
        return resetPasswordRepository.getLastTokenByUserId(userId);
    }
}
