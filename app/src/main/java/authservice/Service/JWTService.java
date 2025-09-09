package authservice.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {
    private  static  final String SECRET = "RXhwZW5zZVRyYWNrZXJKd3RTZWNyZXRLZXkxMjM0NTY3ODkw";

    // 1. make Instance of secret key ,so we can use for create Token
    private Key getSignKey(){
        // Decode the Secret key using decoder method
        byte[] keyByte = Decoders.BASE64.decode(SECRET);
        // Creates a new SecretKey instance for use with HMAC-SHA(HS256) algorithms from keyByte //
        return Keys.hmacShaKeyFor(keyByte);
    }

    // extract claim from jwt token
    public Claims extractAllClaim(String token){
        return  Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // <T> generic return type(you can return any type) , Function<Input Type , return Type>
    public <T> T extractClaim(String token , Function<Claims ,T> claimsResolver){
        final Claims claims = extractAllClaim(token); //extract all claim like username , expire , signature
        return claimsResolver.apply(claims);
    }

    public  String extractUsername(String token){
        return extractClaim(token , Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token , Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token){
        // get Date from token using created extractExpiration method and compare with today's date //
        return extractExpiration(token).before(new Date());
    }

    public Boolean isTokenValid(String token , UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


// Create Token
    public String createToken(Map<String,Object> claims, String username){
        return Jwts.builder()
                .setClaims(claims) //all Claims include in JWT
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis())) // current creation time
                .setExpiration(new Date(System.currentTimeMillis()+1000*60)) // expire time set has after 60min
                .signWith(getSignKey() , SignatureAlgorithm.HS256) // Encode with HS256 hashing Algo
                .compact(); // Compact this all thing in one String
    }

    public String GenerateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }


}
