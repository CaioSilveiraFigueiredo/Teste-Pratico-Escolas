package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

import model.Escola;

public class EscolasDao {    
	public void inserirDadosNoBanco(List<Escola> escolas) {
        String sql = "INSERT INTO escolas (dre, codesc, tipoesc, nomes, nomescofi, ceu, diretoria, subpref, endereco, numero, bairro, cep, tel1, tel2, fax, situacao, coddist, distrito, setor, codinep, cd_cie, eh, fx_etaria, dt_criacao, ato_criacao, dom_criacao, dt_ini_conv, dt_autoriza, dt_extincao, nome_ant, rede, latitude, longitude, database) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = Conexao.conectar();
            conn.setAutoCommit(false); 

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                int count = 0;
                int batchSize = 500;

                for (Escola e : escolas) {
                    stmt.setString(1, e.getDre());
                    setLongOrNull(stmt, 2, e.getCodesc());
                    stmt.setString(3, e.getTipoesc());
                    stmt.setString(4, e.getNomes());
                    stmt.setString(5, e.getNomescofi());
                    stmt.setString(6, e.getCeu());
                    stmt.setString(7, e.getDiretoria());
                    stmt.setString(8, e.getSubpref());
                    stmt.setString(9, e.getEndereco());
                    stmt.setString(10, e.getNumero());
                    stmt.setString(11, e.getBairro());
                    setLongOrNull(stmt, 12, e.getCep());
                    stmt.setString(13, e.getTel1());
                    stmt.setString(14, e.getTel2());
                    setLongOrNull(stmt, 15, e.getFax());
                    stmt.setString(16, e.getSituacao());
                    setLongOrNull(stmt, 17, e.getCoddist());
                    stmt.setString(18, e.getDistrito());
                    setLongOrNull(stmt, 19, e.getSetor());
                    setLongOrNull(stmt, 20, e.getCodinep());
                    setLongOrNull(stmt, 21, e.getCdCie());
                    setLongOrNull(stmt, 22, e.getEh());
                    stmt.setString(23, e.getFxEtaria());
                    setTimestampOrNull(stmt, 24, e.getDtCriacao());
                    stmt.setString(25, e.getAtoCriacao());
                    setTimestampOrNull(stmt, 26, e.getDomCriacao());
                    stmt.setString(27, e.getDtIniConv());
                    stmt.setString(28, e.getDtAutoriza());
                    setLongOrNull(stmt, 29, e.getDtExtincao());
                    stmt.setString(30, e.getNomeAnt());
                    stmt.setString(31, e.getRede());
                    stmt.setBigDecimal(32, e.getLatitude());
                    stmt.setBigDecimal(33, e.getLongitude());
                    setTimestampOrNull(stmt, 34, e.getDatabase());

                    stmt.addBatch();

                    if (++count % batchSize == 0) {
                        stmt.executeBatch();
                    }
                }
                stmt.executeBatch();
                conn.commit(); 
                System.out.println("Importação concluída com sucesso!");
            }

        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    System.err.println("Erro na importação. Realizando Rollback");
                    conn.rollback();
                } catch (SQLException e1) {
                    System.err.println("Erro ao tentar rollback: " + e1.getMessage());
                }
            }
            System.err.println("Erro de Banco de Dados: " + ex.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }
    }

    private void setLongOrNull(PreparedStatement stmt, int idx, Long valor) throws SQLException {
        if (valor != null) stmt.setLong(idx, valor);
        else stmt.setNull(idx, Types.BIGINT);
    }

    private void setTimestampOrNull(PreparedStatement stmt, int idx, java.util.Date valor) throws SQLException {
        if (valor != null) stmt.setTimestamp(idx, new Timestamp(valor.getTime()));
        else stmt.setNull(idx, Types.TIMESTAMP);
    }
}
